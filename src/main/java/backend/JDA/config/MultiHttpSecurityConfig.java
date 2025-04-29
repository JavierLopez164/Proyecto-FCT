package backend.JDA.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity

public class MultiHttpSecurityConfig {

	 @Configuration 
	 public static class SecurityConfigToken extends WebSecurityConfigurerAdapter {
	
		 @Override
		    protected void configure(HttpSecurity http) throws Exception {
			
		    	http.csrf().disable().
		    		addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
		    		.authorizeRequests().antMatchers(HttpMethod.POST, "/cliente/usuario/registrar").permitAll().
		    		antMatchers("/cliente/usuario/verificar").permitAll().
		    		antMatchers("/cliente/usuario/admin/token/mensajes").hasRole("ADMIN");
		    }
	 } 
	
}
