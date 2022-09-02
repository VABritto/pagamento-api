package com.britto.pagamentoapi.pagamento.model.dto;

import com.britto.pagamentoapi.pagamento.model.entity.Transacao;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PagamentoResponse {
	
	private TransacaoResponse transacao;

	public static PagamentoResponse of(Transacao entity) {
		return PagamentoResponse.builder()
								.transacao(
									TransacaoResponse.builder()
										.cartao(entity.getCartao())
										.id(entity.getId().toString())
										.descricao(
											DescricaoResponse.builder()
												.valor(entity.getValor().toString())
												.dataHora(entity.getDataHora())
												.estabelecimento(entity.getEstabelecimento())
												.nsu(entity.getNsu())
												.codigoAutorizacao(entity.getAutorizacao().getCodigoAutorizacao().toString())
												.status(entity.getAutorizacao().getStatus().toString()).build()
										).formaPagamento(
											FormaPagamentoDTO.builder()
												.tipo(entity.getTipo().toString())
												.parcelas(entity.getParcelas().toString()).build()
										).build()
								).build();
	}

}
