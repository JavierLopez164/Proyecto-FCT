package backend.JDA.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class MultiHttpSecurityConfig {


	   @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{		
		    	http.csrf(csrf -> csrf.disable())
		        .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
		        .authorizeHttpRequests(auth -> auth
		            .requestMatchers("/api/administradores/register").permitAll()
		            .requestMatchers("/api/administradores/login").permitAll()
		            .requestMatchers("/api/clientes-registrados/register").permitAll()
		            .requestMatchers("/api/clientes-registrados/login").permitAll()
		          
		            .anyRequest().authenticated());
		    	
		    
	     return http.build();
	 }
	
}
