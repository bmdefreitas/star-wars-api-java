package com.b2wdigital.desafio.starwarsapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.b2wdigital.desafio.starwarsapi.exception.PlanetaNotFoundException;
import com.b2wdigital.desafio.starwarsapi.model.Planeta;
import com.b2wdigital.desafio.starwarsapi.repository.PlanetaRepository;
import com.b2wdigital.desafio.starwarsapi.service.PlanetaService;
import com.b2wdigital.desafio.starwarsapi.service.SwapiService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlanetaServiceImpl implements PlanetaService {
	
	@NonNull
	private final PlanetaRepository planetaRepository;
	
	@NonNull
	private final SwapiService swapiService;
	

	@Override
	public List<Planeta> index() {
		return planetaRepository.findAll();
	}

	@Override
	public Optional<List<Planeta>> search(String name) {
		return planetaRepository.findByNome(name);
	}

	@Override
	public Planeta show(String id) {
		return planetaRepository.findById(id)
				.orElseThrow(() -> new PlanetaNotFoundException(id));		
	}

	@Override
	public Planeta create(Planeta planeta) throws Exception {
		planeta.setQtdeAparicoesFilmes(swapiService.getQtdeAparicoesFilmesByName(planeta.getNome()));
		return planetaRepository.save(planeta); 
	}

	@Override
	public Planeta update(String id, Planeta planeta) {
		planeta.setId(id);
		return show(id) != null ? planetaRepository.save(planeta) : show(id);
	}

	@Override
	public void remove(String id) {
		if(show(id) != null) {
			planetaRepository.deleteById(id);
		}
	}
}
