package com.b2wdigital.desafio.starwarsapi.service;

import java.util.List;
import java.util.Optional;

import com.b2wdigital.desafio.starwarsapi.model.Planeta;

public interface PlanetaService {
	
	List<Planeta> index() ;

	Optional<List<Planeta>> search(String name);
	
	Planeta show(String id);
    
	Planeta create(Planeta planeta) throws Exception;
	
	Planeta update(String id, Planeta planeta);
    
	void remove(String id);
}
