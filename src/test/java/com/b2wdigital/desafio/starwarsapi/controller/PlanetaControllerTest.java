package com.b2wdigital.desafio.starwarsapi.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.b2wdigital.desafio.starwarsapi.exception.PlanetaNotFoundException;
import com.b2wdigital.desafio.starwarsapi.model.Planeta;
import com.b2wdigital.desafio.starwarsapi.service.PlanetaService;
import com.fasterxml.jackson.databind.ObjectMapper;

//@WebMvcTest(PlanetaController.class)
@SpringBootTest
@AutoConfigureMockMvc
class PlanetaControllerTest {
	
	private final String id = "5e4f40acb9454228439c11a4";
	
	private final String name = "Tatooine";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlanetaService service;
	
	@Test
	public void getAllPlanet_200() throws Exception {
		Planeta tatooine = new Planeta(this.id, this.name, "arid", "desert", 5);
		Planeta dagobah = new Planeta("5e4f40b9b9454228439c11a5", "Dagobah", "arid", "desert", 3);
		
		when(service.index()).thenReturn(asList(tatooine, dagobah));
		
		this.mockMvc.perform(get("/api/v1/planetas"))
	      .andDo(print())
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$._embedded").exists())
	      .andExpect(jsonPath("$._embedded.planetaList[*].id").isNotEmpty())
	      .andExpect(jsonPath("$._embedded.planetaList[*]._links").exists())
	      .andExpect(jsonPath("$._embedded.planetaList[0].id").value(this.id))
	      .andExpect(jsonPath("$._embedded.planetaList[0].nome").value(this.name))
	      .andExpect(jsonPath("$._embedded.planetaList[1].id").value("5e4f40b9b9454228439c11a5"))
	      .andExpect(jsonPath("$._embedded.planetaList[1].nome").value("Dagobah"))
	      .andExpect(jsonPath("$._links").exists());
		
		reset(service);
	}
	 
	@Test
	public void getPlanetById_200() throws Exception {		
		when(service.show(this.id)).thenReturn(new Planeta(this.id, this.name, "arid", "desert", 5));
		
		this.mockMvc.perform(get("/api/v1/planetas/{id}", this.id))
	      	.andDo(print())
	      	.andExpect(status().isOk())
	      	.andExpect(jsonPath("$.id").value(this.id));
		
		reset(service);
	}
	
	@Test
    public void getPlanetByIdNotFound_404() throws Exception {
		when(service.show("5e4f40acb9454228439c11a")).thenThrow(new PlanetaNotFoundException("5e4f40acb9454228439c11a"));
		
		this.mockMvc.perform(get("/api/v1/planetas/{id}", "5e4f40acb9454228439c11a"))
			.andExpect(status().isNotFound());
		
		reset(service);
    }
	
	@Test
	public void getPlanetByName_200() throws Exception {		
		when(service.search("Dagobah")).thenReturn(Optional.of(asList(new Planeta(this.id, "Dagobah", "arid", "desert", 5))));
		
		this.mockMvc.perform(get("/api/v1/planetas/search?nome=Dagobah"))
	      	.andDo(print())
	      	.andExpect(status().isOk())
	      	.andExpect(jsonPath("$._embedded").exists())
	      	.andExpect(jsonPath("$._embedded.planetaList[*].id").isNotEmpty())
		    .andExpect(jsonPath("$._embedded.planetaList[*]._links").exists())
		    .andExpect(jsonPath("$._embedded.planetaList[0].id").value(this.id))
		    .andExpect(jsonPath("$._embedded.planetaList[0].nome").value("Dagobah"))
		    .andExpect(jsonPath("$._links").exists());
		
		reset(service);
	}
	
	@Test
	public void createPlanet_200() throws Exception {
		Planeta tatooine = new Planeta(this.id, this.name, "arid", "desert", 5);
		when(service.create(new Planeta(null, this.name, "arid", "desert", 5))).thenReturn(tatooine);
		
		this.mockMvc.perform(post("/api/v1/planetas").content(asJsonString(new Planeta(null, this.name, "arid", "desert", 5)))
			.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isCreated())
	      	.andExpect(jsonPath("$.id").exists())
	      	.andExpect(jsonPath("$.id").value(this.id))
	      	.andExpect(jsonPath("$.nome").value(this.name))
	      	.andExpect(jsonPath("$.clima").value("arid"))
	      	.andExpect(jsonPath("$.terreno").value("desert"))
	      	.andExpect(jsonPath("$.qtdeAparicoesFilmes").value(5))
	      	.andExpect(jsonPath("$._links").exists());
		
		reset(service);
	}
	
	@Test
	public void createPlanet_400() throws Exception {		
		this.mockMvc.perform(post("/api/v1/planetas").content(asJsonString(new Planeta(null, null, "arid", "desert", 5)))
			.contentType(MediaType.APPLICATION_JSON))
	      	.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updatePlanet_200() throws Exception {
		Planeta tatooineModified = new Planeta(this.id, this.name, "arid", "temperate", 5);
		
		when(service.update(this.id, tatooineModified)).thenReturn(tatooineModified);
		
		this.mockMvc.perform(put("/api/v1/planetas/{id}", this.id).content(asJsonString(tatooineModified))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(this.id))
		      	.andExpect(jsonPath("$.nome").value(this.name))
		      	.andExpect(jsonPath("$.clima").value("arid"))
		      	.andExpect(jsonPath("$.terreno").value("temperate"))
		      	.andExpect(jsonPath("$.qtdeAparicoesFilmes").value(5))
		      	.andExpect(jsonPath("$._links").exists());
		
		reset(service);
	}
	
	@Test
	public void updatePlanet_400() throws Exception {
		Planeta tatooineModified = new Planeta(this.id, null, "arid", "temperate", 5);
		
		this.mockMvc.perform(put("/api/v1/planetas/{id}", this.id).content(asJsonString(tatooineModified))
				.contentType(MediaType.APPLICATION_JSON))
      			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updatePlanet_404() throws Exception {
		
		String id = "5e4f40acb9454228439c11a";
		
		Planeta tatooineModified = new Planeta(this.id, this.name, "arid", "temperate", 5);
		
		when(service.update(id, tatooineModified)).thenThrow(new PlanetaNotFoundException(id));
		
		this.mockMvc.perform(put("/api/v1/planetas/{id}", id).content(asJsonString(tatooineModified))
				.contentType(MediaType.APPLICATION_JSON))
      			.andExpect(status().isNotFound());
		
		reset(service);
	}
	
	@Test
	public void deletePlanet_204() throws Exception {
		String id = "5e701182e7560117fd839bb2";
		
		when(service.show(id)).thenReturn(new Planeta(id, this.name, "arid", "desert", 5));
		
		this.mockMvc.perform(delete("/api/v1/planetas/{id}", "5e701182e7560117fd839bb2"))
	        .andExpect(status().isNoContent());
	}
	
	@Test
	public void deletePlanet_404() throws Exception {
		
		String id = "5e4f40acb9454228439c11a";
		
		Mockito.doThrow(new PlanetaNotFoundException(id)).when(service).remove(id);
		
		this.mockMvc.perform(delete("/api/v1/planetas/{id}", id))
	        .andExpect(status().isNotFound());
		
		reset(service);
	}
	 
	private static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
