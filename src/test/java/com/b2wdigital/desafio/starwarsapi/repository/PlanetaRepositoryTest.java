package com.b2wdigital.desafio.starwarsapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.b2wdigital.desafio.starwarsapi.model.Planeta;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootTest
@ActiveProfiles("test")
class PlanetaRepositoryTest {

	@Autowired
	public PlanetaRepository planetaRepository;
	   
    @BeforeEach
    public void initEach(){
    	this.planetaRepository.deleteAll();
    }	

	@Test
	public void createPlanet() {
		Planeta planetaSalvo = this.planetaRepository.save(new Planeta(null, "Naboo", "temperate", "verdant", 4));
		
		assertThat(planetaSalvo.getId()).isNotNull();
		assertThat(planetaSalvo.getNome()).isEqualTo("Naboo");
		assertThat(planetaSalvo.getClima()).isEqualTo("temperate");
		assertThat(planetaSalvo.getTerreno()).isEqualTo("verdant");
		assertThat(planetaSalvo.getQtdeAparicoesFilmes()).isEqualTo(4);
	}
	
	@Test
	public void updatePlanet() {
		Planeta naboo = this.planetaRepository.save(new Planeta(null, "Naboo", "temperate", "verdant", 4));
		
		naboo.setClima("arid");
		
		Planeta planetaAtualizado = this.planetaRepository.save(naboo);
		
		assertThat(planetaAtualizado.getId()).isNotNull();
		assertThat(planetaAtualizado.getNome()).isEqualTo("Naboo");
		assertThat(planetaAtualizado.getClima()).isEqualTo("arid");
		assertThat(planetaAtualizado.getTerreno()).isEqualTo("verdant");
		assertThat(planetaAtualizado.getQtdeAparicoesFilmes()).isEqualTo(4);
	}
	
	@Test
	public void listPlanets() {
		Planeta naboo = this.planetaRepository.save(new Planeta(null, "Naboo", "temperate", "verdant", 4));
		Planeta dagobah = this.planetaRepository.save(new Planeta(null, "Dagobah", "arid", "desert", 3));
		
		List<Planeta> planetas = this.planetaRepository.findAll();
		
		assertThat(planetas).isNotNull();
		assertThat(planetas).isNotEmpty();
		assertThat(planetas).contains(dagobah, naboo);
		assertThat(planetas).hasSize(2);
	}
	
	@Test
	public void planetById() {
		Planeta naboo = this.planetaRepository.save(new Planeta(null, "Naboo", "temperate", "verdant", 4));
		
		Optional<Planeta> planeta = this.planetaRepository.findById(naboo.getId());
		
		assertThat(planeta.get().getId()).isNotNull();
		assertThat(planeta.get().getNome()).isEqualTo("Naboo");
		assertThat(planeta.get().getClima()).isEqualTo("temperate");
		assertThat(planeta.get().getTerreno()).isEqualTo("verdant");
		assertThat(planeta.get().getQtdeAparicoesFilmes()).isEqualTo(4);
	}
	
	@Test
	public void planetByName() {
		Planeta naboo = this.planetaRepository.save(new Planeta(null, "Naboo", "temperate", "verdant", 4));
		Planeta dagobah = this.planetaRepository.save(new Planeta(null, "Dagobah", "arid", "desert", 3));
		
		List<Planeta> planetas = this.planetaRepository.findByNome(naboo.getNome()).orElse(new ArrayList<Planeta>());
		
		assertThat(planetas).isNotNull();
		assertThat(planetas).isNotEmpty();
		assertThat(planetas).contains(naboo);
		assertThat(planetas).doesNotContain(dagobah);
		assertThat(planetas).hasSize(1);
	}
}
