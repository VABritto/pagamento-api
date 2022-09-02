package com.britto.pagamentoapi.pagamento.exception;

import java.util.List;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 6029138331190550547L;
	private final List<String> validations;

	public ValidationException(String message) {
		super(message);
		this.validations = null;
	}
	
	public ValidationException(String message, List<String> validations) {
		super(message);
		this.validations = validations;
	}
}
