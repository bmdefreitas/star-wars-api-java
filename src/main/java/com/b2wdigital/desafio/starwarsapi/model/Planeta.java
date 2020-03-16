package com.b2wdigital.desafio.starwarsapi.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "planetas")
public class Planeta {
	
	@Id
	private String id;
	
	@NotNull
	@NotEmpty
	private String nome;
	
	@NotNull
	@NotEmpty
	private String clima;
	
	@NotNull
	@NotEmpty
	private String terreno;
	
	
	private Integer qtdeAparicoesFilmes;
}
