package com.b2wdigital.desafio.starwarsapi.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.b2wdigital.desafio.starwarsapi.dto.PlanetaSwapiDto;
import com.b2wdigital.desafio.starwarsapi.dto.ResponseSwapiDto;
import com.b2wdigital.desafio.starwarsapi.exception.PlanetaNotFoundException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class SwapiRepository {
	
	@Value("${swapi.url}")
	private String urlSwapi;
	
	@NonNull
	private final RestTemplate restTemplate;
	
	@NonNull
	private final CacheManager cache;
	
	@CachePut("planetas")
	@HystrixCommand(
			fallbackMethod = "getPlanetFromCacheOrNull", 
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="60")
			})
	public PlanetaSwapiDto findPlanetsByName(String name) throws Exception {
		
		ResponseSwapiDto response = restTemplate.getForObject(urlSwapi + "/planets?search=" + name,
				ResponseSwapiDto.class);
		
		log.info("Planetas encontrados: " + response.getResults().toString());
		
		return response.getResults().stream()
				.findFirst()
				.orElseThrow(() -> new PlanetaNotFoundException(name));
	}
	
	public PlanetaSwapiDto getPlanetFromCacheOrNull(String name) {
		ValueWrapper p = cache.getCache("planetas").get(name);
		return p != null ? (PlanetaSwapiDto) p.get() : null;
	}
}
