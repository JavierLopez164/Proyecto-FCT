package backend.JDA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableMongoAuditing
@EnableMongoRepositories
@SpringBootApplication
@EnableScheduling
public class JdaApplication  {
	public static void main(String[] args) {
		SpringApplication.run(JdaApplication.class, args);
	}
}

