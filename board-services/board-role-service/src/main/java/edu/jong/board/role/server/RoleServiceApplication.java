package edu.jong.board.role.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import edu.jong.board.BoardConstants;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = BoardConstants.ROOT_PACKAGE)
@SpringBootApplication(scanBasePackages = BoardConstants.ROOT_PACKAGE)
public class RoleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoleServiceApplication.class, args);
	}
}
