package com.britto.pagamentoapi.pagamento.model.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DescricaoRequest {

	private String valor;
	private LocalDateTime dataHora;
	private String estabelecimento;
}
