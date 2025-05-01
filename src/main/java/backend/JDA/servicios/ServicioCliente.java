package backend.JDA.servicios;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.Rol;
import backend.JDA.repositorios.ClienteRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class ServicioCliente implements IServicioCliente {
	@Autowired ClienteRepositorio clienteDao;
	private final String secretKey = "mySecretKey";

	private final long tiempo = 600000;
	@Override
	public boolean registrarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		boolean exito = false;
		if(!clienteDao.existsById((cliente.getEmail()))){
			cliente.setToken(getJWTToken(cliente.getNombre(),cliente.getRol()));
			clienteDao.save(cliente);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public String login(String email, String password) {
		// TODO Auto-generated method stub
		
		return clienteDao.usuarioCoincidente(email, password);
	}
	private String getJWTToken(String nombre,Rol rol) {

		List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(rol.name()));

		String token = Jwts
				.builder()
				.setId(UUID.randomUUID().toString())
				.setSubject(nombre)
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
