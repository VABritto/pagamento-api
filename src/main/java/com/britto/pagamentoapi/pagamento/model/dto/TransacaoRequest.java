package com.britto.pagamentoapi.pagamento.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransacaoRequest {
	
	private String cartao;
	private String id;
	private DescricaoRequest descricao;
	private FormaPagamentoDTO formaPagamento;
}
