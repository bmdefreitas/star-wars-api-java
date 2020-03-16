package com.b2wdigital.desafio.starwarsapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.b2wdigital.desafio.starwarsapi.repository.SwapiRepository;
import com.b2wdigital.desafio.starwarsapi.service.SwapiService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SwapiServiceImpl implements SwapiService {
	
	@Value("${swapi.url}")
	private String urlSwapi;
	
	@NonNull
	private final SwapiRepository swapiRepository;
	

	@Override
	public Integer getQtdeAparicoesFilmesByName(String name) throws Exception {		
		return Optional.ofNullable(swapiRepository.findPlanetsByName(name).getFilms())
				.map(List::size)
				.orElse(0);
	}
	
	
}
