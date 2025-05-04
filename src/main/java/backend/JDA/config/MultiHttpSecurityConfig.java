package backend.JDA.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MultiHttpSecurityConfig {
	 @Autowired
	    JWTAuthorizationFilter jwtAuthorizationFilter;
    @Bean
     SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> {
					csrf.disable();
			})
           
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/clientes/login","/api/clientes/register").permitAll()
                .requestMatchers( "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**").permitAll()
                .requestMatchers("/api/clientes/mensaje").hasRole("USER")).
                
          
                addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            ;

        return http.build();
    }
}
