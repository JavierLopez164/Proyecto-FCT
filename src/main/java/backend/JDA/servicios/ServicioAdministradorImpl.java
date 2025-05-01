package backend.JDA.servicios;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import backend.JDA.modelo.Administrador;
import backend.JDA.repositorios.AdministradorRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ServicioAdministradorImpl implements IServicioAdministrador {

	private final String secretKey = "mySecretKey";

	private final long tiempo = 600000;
	@Autowired
	private AdministradorRepositorio administradorRepositorio;

	@Override
	public boolean insert(Administrador administrador) {
		boolean exito = false;

		if(!administradorRepositorio.existsById(administrador.getEmail())) {
			administrador.setToken(getJWTToken(administrador.getNombre()));
			administradorRepositorio.save(administrador);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean update(Administrador administrador) {
		boolean exito = false;

		if(!administradorRepositorio.existsById(administrador.getEmail())) {
			administradorRepositorio.save(administrador);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean delete(String email) {
		boolean exito = false;

		if(administradorRepositorio.existsById(email)) {
			administradorRepositorio.deleteById(email);
			exito = true;
		}

		return exito;
	}

	@Override
	public Optional<Administrador> findById(String email) {
		return administradorRepositorio.findById(email);
	}


	@Override
	public String administradorCoincidente(String email, String password) {
		return administradorRepositorio.administradorCoincidente(email, password);
	}
	
	private String getJWTToken(String nombre) {

		List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));

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
