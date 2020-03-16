package com.b2wdigital.desafio.starwarsapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.b2wdigital.desafio.starwarsapi.component.PlanetaModelAssembler;
import com.b2wdigital.desafio.starwarsapi.dto.PlanetaSwapiDto;
import com.b2wdigital.desafio.starwarsapi.model.Planeta;
import com.b2wdigital.desafio.starwarsapi.repository.SwapiRepository;
import com.b2wdigital.desafio.starwarsapi.service.PlanetaService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/planetas")
@RequiredArgsConstructor
public class PlanetaController {
	
	@Autowired
	SwapiRepository swapiRepository;
	
	@NonNull
	private final PlanetaService planetaService;
	
	@NonNull
	private final PlanetaModelAssembler planetaAssembler;
	
	
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Planeta>>> index() {
		List<EntityModel<Planeta>> planetas = planetaService.index().stream()
			    .map(planetaAssembler::toModel)
			    .collect(Collectors.toList());

		return new ResponseEntity<>(new CollectionModel<>(planetas,
		    linkTo(methodOn(PlanetaController.class).index()).withSelfRel()), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Planeta>> show(@PathVariable String id) {
		return new ResponseEntity<>(planetaAssembler.toModel(planetaService.show(id)), HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<CollectionModel<EntityModel<Planeta>>> search(@RequestParam("nome") String nome) {
		List<EntityModel<Planeta>> planetas = planetaService.search(nome)
				.orElse(new ArrayList<Planeta>())
				.stream()
			    .map(planetaAssembler::toModel)
			    .collect(Collectors.toList());

		return new ResponseEntity<>(new CollectionModel<>(planetas,
		    linkTo(methodOn(PlanetaController.class).index()).withSelfRel()), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<Planeta>> create(@Valid @RequestBody Planeta planeta) throws Exception {
		return new ResponseEntity<>(planetaAssembler.toModel(planetaService.create(planeta)), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Planeta>> update(@PathVariable String id, @Valid @RequestBody Planeta planeta) {
		return new ResponseEntity<>(planetaAssembler.toModel(planetaService.update(id, planeta)), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		planetaService.remove(id);
	}
	
	@GetMapping("/test")
	public PlanetaSwapiDto test(@RequestParam("nome") String nome) throws Exception {
		return swapiRepository.findPlanetsByName(nome);
	}	

}
