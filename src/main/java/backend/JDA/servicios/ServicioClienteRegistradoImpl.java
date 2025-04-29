package backend.JDA.servicios;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.ClienteRegistrado;
import backend.JDA.repositorios.ClienteRepositorio;

@Service
public class ServicioClienteRegistradoImpl implements IServicioClienteRegistrado {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;

	private final String secretKey = "mySecretKey";

	private final long tiempo = 600000;

	@Override
	public boolean insert(ClienteRegistrado cliente) {
		boolean exito = false;
		
		if(!clienteRepositorio.existsByEmail((cliente.getEmail()))){
			cliente.setToken(getJWTToken(cliente.getEmail()));
			clienteRepositorio.save(cliente);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(ClienteRegistrado cliente) {
		boolean exito = false;
		
		if(!clienteRepositorio.existsByEmail(cliente.getEmail())) {
			clienteRepositorio.save(cliente);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String email, String password) {
		boolean exito = false;
		Optional<ClienteRegistrado> cliente = login(email, password);
		
		if(cliente.isPresent()) {
			clienteRepositorio.delete(cliente.get());
			exito = true;
		}
		
		return exito;
	}

	@Override
	public Optional<ClienteRegistrado> login(String email, String contrasenia) {
		return Optional.ofNullable(clienteRepositorio.findByEmailAndContrasenia(email, contrasenia));
	}

	@Override
	public String usuarioCoincidente(String email, String password) {
		return clienteRepositorio.usuarioCoincidente(email, password);
	}
	private String getJWTToken(String username) {

		List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority());

		String token = Jwts
				.builder()
				.setId(UUID.randomUUID().toString())
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tiempo))
				.signWith(SignatureAlgorithm.HS256,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
