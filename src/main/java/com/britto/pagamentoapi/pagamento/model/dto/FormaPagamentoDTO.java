package com.britto.pagamentoapi.pagamento.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormaPagamentoDTO {

	private String tipo;
	private String parcelas;
}
