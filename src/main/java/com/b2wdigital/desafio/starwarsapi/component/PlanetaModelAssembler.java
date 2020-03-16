package com.b2wdigital.desafio.starwarsapi.component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.b2wdigital.desafio.starwarsapi.controller.PlanetaController;
import com.b2wdigital.desafio.starwarsapi.model.Planeta;

@Component
public class PlanetaModelAssembler implements RepresentationModelAssembler<Planeta, EntityModel<Planeta>> {
	
	@Override
	public EntityModel<Planeta> toModel(Planeta planeta) {		
		return new EntityModel<>(planeta,
				linkTo(methodOn(PlanetaController.class).show(planeta.getId())).withSelfRel(),
				linkTo(methodOn(PlanetaController.class).index()).withRel("planetas"));
	}
}
