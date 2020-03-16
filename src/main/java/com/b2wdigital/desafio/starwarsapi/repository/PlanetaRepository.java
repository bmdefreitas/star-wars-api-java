package com.b2wdigital.desafio.starwarsapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.b2wdigital.desafio.starwarsapi.model.Planeta;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {
	Optional<List<Planeta>> findByNome(String nome);
}
