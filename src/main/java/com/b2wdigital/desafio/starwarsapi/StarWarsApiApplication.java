package com.b2wdigital.desafio.starwarsapi;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableCaching
public class StarWarsApiApplication {
	
	@Bean
	public RestTemplate rest(RestTemplateBuilder restBuilder) {
		return restBuilder
				.setConnectTimeout(Duration.ofSeconds(1000))
	            .setReadTimeout(Duration.ofSeconds(1000))
	            .build();
	}

	public static void main(String[] args) {
		SpringApplication.run(StarWarsApiApplication.class, args);
	}

}
