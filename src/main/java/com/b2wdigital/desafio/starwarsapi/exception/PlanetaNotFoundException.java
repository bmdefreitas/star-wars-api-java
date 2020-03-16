package com.b2wdigital.desafio.starwarsapi.exception;

public class PlanetaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4156076169557585372L;

	public PlanetaNotFoundException(String id) {
		super("Não foi possível encontrar um planeta com o ID: " + id);
	}
		
}
