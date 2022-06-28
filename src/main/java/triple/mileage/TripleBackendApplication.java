package triple.mileage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TripleBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripleBackendApplication.class, args);
	}

}
