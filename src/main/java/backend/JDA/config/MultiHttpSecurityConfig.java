package backend.JDA.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/api/clientes/login",
                                "/api/clientes/register",
                                "/api/comentarios/lista",
                                "api/comentarios/promedio"
                        ).permitAll()

                        // Rutas públicas
                        .requestMatchers("/api/comida/**").permitAll()
                        .requestMatchers("/api/pedidos/**").permitAll()
                        .requestMatchers("/api/stripe/**").permitAll()
                        
                        // Swagger
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // Comentarios
                        .requestMatchers("/api/comentarios/crear").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/comentarios/eliminar").hasRole("ADMIN")

                        // Consultar clientes

                        .requestMatchers("/api/clientes/consultar/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/clientes/actualizar").hasAnyRole("USER", "ADMIN")                   
                        //fotos
                        .requestMatchers("/api/fotos/**").permitAll()

                        //comidas
                        .requestMatchers("/api/comida/obtenerNombresRestaurante").hasRole("USER")
                        .requestMatchers("/api/comida/obtenerComidasDeUnRestaurantes").hasRole("USER")
                        //chatbot
                        .requestMatchers("/api/chatbot/mandarMensaje").permitAll()
                        //verificacion al crear un usuario
                        .requestMatchers("api/verificacion/verificar").permitAll()
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
