package com.b2wdigital.desafio.starwarsapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.b2wdigital.desafio.starwarsapi.exception.PlanetaNotFoundException;

@ControllerAdvice
public class PlanetaNotFoundControllerAdvice {
	@ResponseBody
	@ExceptionHandler(PlanetaNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String planetaNotFoundHandler(PlanetaNotFoundException ex) {
		return ex.getMessage();
	}
}
