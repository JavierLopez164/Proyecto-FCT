package backend.JDA.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	   // Cargar Beans para inyectar
	
	
    @Bean(initMethod = "inicializarBean", destroyMethod = "finalizarBean")
    DtoConverter dtoConverter() {
	    return new DtoConverter();
	}
    
    @Bean
     PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean 
     RestTemplate restTemplate() {
    	return new RestTemplate();
    }
}
