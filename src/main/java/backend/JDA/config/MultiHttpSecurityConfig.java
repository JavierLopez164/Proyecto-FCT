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
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable())
           
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/clientes/**").permitAll()
                .requestMatchers("/swagger-ui/index.html").permitAll()
                .requestMatchers("/api/clientes-registrados/actualizarRegistrado").hasRole("USER")
          
                .anyRequest().authenticated()).
                addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
            ;

        return http.build();
    }
}
