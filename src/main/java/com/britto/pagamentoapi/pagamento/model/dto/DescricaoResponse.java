package com.britto.pagamentoapi.pagamento.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DescricaoResponse {

	private String valor;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataHora;
	private String estabelecimento;
	private String nsu;
	private String codigoAutorizacao;
	private String status;
}
