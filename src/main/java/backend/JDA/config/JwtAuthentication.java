package backend.JDA.config;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import backend.JDA.modelo.Rol;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtAuthentication {
	public String getJWTToken(String email,Rol rol) {

		List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(rol.name()));

		String token = Jwts
				.builder()
				.setId("WashabiJWT")
				.setSubject(email)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JwtConstant.TOKEN_EXPIRATION_TIME))
				.signWith(JwtConstant.getSigningKey(JwtConstant.SUPER_SECRET_KEY), SignatureAlgorithm.HS512).compact();

		return "Bearer " + token;
	}
}
