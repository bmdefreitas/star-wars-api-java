package com.b2wdigital.desafio.starwarsapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.b2wdigital.desafio.starwarsapi.dto.PlanetaSwapiDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootTest
class SwapiRepositoryTest {
	
	@Autowired
	public SwapiRepository swapiRepository;

	@Test
	void test() throws Exception {
		
		PlanetaSwapiDto naboo = this.swapiRepository.findPlanetsByName("Naboo");
		
		assertThat(naboo.getName()).isNotNull();
		assertThat(naboo.getName()).isEqualTo("Naboo");
		assertThat(naboo.getClimate()).isEqualTo("temperate");
		assertThat(naboo.getTerrain()).isEqualTo("grassy hills, swamps, forests, mountains");
		assertThat(naboo.getFilms()).isNotNull();
		assertThat(naboo.getFilms()).isNotEmpty();
		assertThat(naboo.getFilms()).hasSize(4);		
	}

}
