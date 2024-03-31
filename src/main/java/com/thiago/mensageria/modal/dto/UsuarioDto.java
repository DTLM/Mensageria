package com.thiago.mensageria.modal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDto {

	@JsonIgnore(value = false)
	private String username;
	@JsonIgnore(value = false)
	private String password;
}
