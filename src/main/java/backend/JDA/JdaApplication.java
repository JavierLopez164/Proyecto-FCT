package backend.JDA;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JdaApplication  {
	public static void main(String[] args) {
		SpringApplication.run(JdaApplication.class, args);
	}
}

