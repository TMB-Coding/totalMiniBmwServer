package com.totalMiniBmw.tmb_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
		basePackages = "com.totalMiniBmw.tmb_server.repository")
@EntityScan("com.totalMiniBmw.tmb_server.entities")
public class TmbServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmbServerApplication.class, args);
	}

}
