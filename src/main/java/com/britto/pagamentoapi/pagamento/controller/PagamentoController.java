package com.britto.pagamentoapi.pagamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.britto.pagamentoapi.pagamento.exception.ValidationException;
import com.britto.pagamentoapi.pagamento.model.dto.ErrorDTO;
import com.britto.pagamentoapi.pagamento.model.dto.PagamentoRequest;
import com.britto.pagamentoapi.pagamento.model.dto.PagamentoResponse;
import com.britto.pagamentoapi.pagamento.service.PagamentoService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/pagamento")
@CrossOrigin
@Log4j2
public class PagamentoController {

	@Autowired
	private PagamentoService service;

	@GetMapping("/consultar/{id}")
	public ResponseEntity<PagamentoResponse> consultar(@PathVariable Long id) {
		return ResponseEntity.ok(service.consultar(id));
	}
	
	@GetMapping("/consultar/todo")
	public ResponseEntity<Page<PagamentoResponse>> consultarTodos(Pageable pageable) {
		return ResponseEntity.ok(service.consultarTodos(pageable));
	}
	
	@PutMapping("/estornar/{id}")
	public ResponseEntity<PagamentoResponse> estornar(@PathVariable Long id) {
		return ResponseEntity.ok(service.estornar(id));
	}
	
	@PostMapping("/transacionar")
	public ResponseEntity<PagamentoResponse> transacionar(PagamentoRequest request) {
		return ResponseEntity.ok(service.salvar(request));
	}
	
	@ExceptionHandler(ValidationException.class)
	private ResponseEntity<ErrorDTO> handleValidationException(ValidationException e) {
		log.error(e);
		return ResponseEntity.badRequest().body(new ErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage(), e.getValidations()));
	}
	
	@ExceptionHandler(Exception.class)
	private ResponseEntity<ErrorDTO> handleUnexpectedException(Exception e) {
		log.error("Erro Inesperado:", e);
		return ResponseEntity.badRequest().body(new ErrorDTO(HttpStatus.BAD_REQUEST, 
														"Erro inesperado. Por favor entre em contato com a administração."));
	}
}
