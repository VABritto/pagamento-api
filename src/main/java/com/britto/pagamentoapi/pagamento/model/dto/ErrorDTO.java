package com.britto.pagamentoapi.pagamento.model.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ErrorDTO {

	private final HttpStatus status;
	private final String message;
	private final List<String> validations;
	
	public ErrorDTO(HttpStatus status, String message, List<String> validations) {
		this.status = status;
		this.message = message;
		this.validations = validations;
	}
	
	public ErrorDTO(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
		this.validations = null;
	}
}
