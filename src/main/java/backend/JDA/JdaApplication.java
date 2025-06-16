package backend.JDA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableMongoAuditing
//@EnableMongoRepositories
@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
public class JdaApplication  {
	public static void main(String[] args) {
		SpringApplication.run(JdaApplication.class, args);
	}
}

