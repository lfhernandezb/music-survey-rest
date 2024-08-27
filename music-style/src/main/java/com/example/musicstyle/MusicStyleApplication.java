package com.example.musicstyle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan("com.example")
@SpringBootApplication
// habilitamos descubrimiento de microservicios
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.example.musicstyle.persistence.crud")
@EntityScan(basePackages = "com.example.library.persistence.entity")
public class MusicStyleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicStyleApplication.class, args);
	}

}
