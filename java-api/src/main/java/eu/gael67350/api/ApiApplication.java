package eu.gael67350.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication(scanBasePackages={"eu.gael67350.api"})
@OpenAPIDefinition(
	info = @Info(
		title = "JAVA Spring API",
		version = "1.0",
		description = "A simple Java API with Spring Boot"
	)
)
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	
	
}
