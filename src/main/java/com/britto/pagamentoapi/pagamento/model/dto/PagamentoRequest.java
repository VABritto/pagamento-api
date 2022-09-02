package com.britto.pagamentoapi.pagamento.model.dto;

import com.britto.pagamentoapi.pagamento.model.entity.Autorizacao;
import com.britto.pagamentoapi.pagamento.model.entity.Transacao;
import com.britto.pagamentoapi.pagamento.model.enumerator.TipoFormaPagamento;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagamentoRequest {

	private TransacaoRequest transacao;

	public static Transacao entityOf(PagamentoRequest request, Autorizacao autorizacao) {
		return Transacao.builder()
						.id(request.getTransacao().getId() != null ? Long.valueOf(request.getTransacao().getId()) : null)
						.cartao(request.getTransacao().getCartao())
						.valor(Double.valueOf(request.getTransacao().getDescricao().getValor()))
						.dataHora(request.getTransacao().getDescricao().getDataHora())
						.estabelecimento(request.getTransacao().getDescricao().getEstabelecimento())
						.autorizacao(autorizacao)
						.tipo(TipoFormaPagamento.getEnum(request.getTransacao().getFormaPagamento().getTipo()))
						.parcelas(Integer.valueOf(request.getTransacao().getFormaPagamento().getParcelas()))
						.build();
	}
}
