package com.b2wdigital.desafio.starwarsapi.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties()
public class ResponseSwapiDto {
	
	private Integer count;
	
	private List<PlanetaSwapiDto> results;
}