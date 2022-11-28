package edu.jong.board.token.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.jong.board.BoardConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = BoardConstants.ROOT_PACKAGE)
@SpringBootApplication(scanBasePackages = BoardConstants.ROOT_PACKAGE)
public class TokenServiceApplication {

	@Bean
	OpenAPI openAPI(
			@Value("${springdoc.title}") String title, 
			@Value("${springdoc.version}") String version) {
		return new OpenAPI().info(new Info().title(title).version(version));
	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(TokenServiceApplication.class, args);
	}
}
