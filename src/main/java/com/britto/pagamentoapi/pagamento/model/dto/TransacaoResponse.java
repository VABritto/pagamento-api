package com.britto.pagamentoapi.pagamento.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransacaoResponse {

	private String cartao;
	private String id;
	private DescricaoResponse descricao;
	private FormaPagamentoDTO formaPagamento;
}
