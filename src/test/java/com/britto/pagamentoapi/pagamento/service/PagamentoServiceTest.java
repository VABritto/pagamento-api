package com.britto.pagamentoapi.pagamento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.britto.pagamentoapi.pagamento.exception.ValidationException;
import com.britto.pagamentoapi.pagamento.model.dto.DescricaoRequest;
import com.britto.pagamentoapi.pagamento.model.dto.FormaPagamentoDTO;
import com.britto.pagamentoapi.pagamento.model.dto.PagamentoRequest;
import com.britto.pagamentoapi.pagamento.model.dto.PagamentoResponse;
import com.britto.pagamentoapi.pagamento.model.dto.TransacaoRequest;
import com.britto.pagamentoapi.pagamento.model.entity.Autorizacao;
import com.britto.pagamentoapi.pagamento.model.entity.Transacao;
import com.britto.pagamentoapi.pagamento.model.enumerator.StatusDescricao;
import com.britto.pagamentoapi.pagamento.model.enumerator.TipoFormaPagamento;
import com.britto.pagamentoapi.pagamento.repository.TransacaoRepository;
import com.britto.pagamentoapi.pagamento.util.DateUtils;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

	private static final Long ID = 123456L;
	private static final String DEZESSEIS_DIGITOS = "4444123456781234";
	private static final String QUINZE_DIGITOS = "100023568900001";
	private static final String DATA = "01/05/2021 18:30:00";
	private static final String TEXTO = "PetShop Mundo cão";

	@InjectMocks
	PagamentoService pagamentoService;
	
	@Mock
	TransacaoRepository repository;
	
	@Test
	void testSuccess() {
		PagamentoRequest request = PagamentoRequest.builder().transacao(
																TransacaoRequest.builder().cartao(DEZESSEIS_DIGITOS).id(null)
																				.descricao(
																						DescricaoRequest.builder()
																						.valor("500.50")
																						.dataHora(DateUtils.diaMesAnoHorario(DATA))
																						.estabelecimento(TEXTO).build())
																				.formaPagamento(
																						FormaPagamentoDTO.builder()
																						.tipo(TipoFormaPagamento.AVISTA.getValue())
																						.parcelas("1").build()).build()).build();
		Autorizacao autorizacao = Autorizacao.builder().status(StatusDescricao.AUTORIZADO).build();
		Transacao transacao = PagamentoRequest.entityOf(request, autorizacao);
		Autorizacao autorizacaoResponse = Autorizacao.builder().codigoAutorizacao(ID).status(StatusDescricao.AUTORIZADO).build();
		Transacao transacaoRetorno = PagamentoRequest.entityOf(request, autorizacaoResponse);
		transacaoRetorno.setId(ID);

		when(repository.save(transacao)).thenReturn(transacaoRetorno);

		try {
			assertEquals(PagamentoResponse.of(transacaoRetorno), pagamentoService.salvar(request));
		} catch (ValidationException e) {
			fail();
		}										
	}
	
	@Test
	void testError() {
		PagamentoRequest request = PagamentoRequest.builder().transacao(
																TransacaoRequest.builder().cartao(null).id(null)
																				.descricao(
																						DescricaoRequest.builder()
																						.valor(null)
																						.dataHora(DateUtils.diaMesAnoHorario(DATA))
																						.estabelecimento(null).build())
																				.formaPagamento(
																						FormaPagamentoDTO.builder()
																						.tipo(null)
																						.parcelas(null).build()).build()).build();
		
		Assertions.assertThrows(ValidationException.class, () -> pagamentoService.salvar(request));
	}
	
	@Test
	void testTipoInvalidoParcelamento() {
		PagamentoRequest request = PagamentoRequest.builder().transacao(
				TransacaoRequest.builder().cartao(DEZESSEIS_DIGITOS).id(null)
								.descricao(
										DescricaoRequest.builder()
										.valor("500.50")
										.dataHora(DateUtils.diaMesAnoHorario(DATA))
										.estabelecimento(TEXTO).build())
								.formaPagamento(
										FormaPagamentoDTO.builder()
										.tipo(TipoFormaPagamento.AVISTA.getValue())
										.parcelas("5").build()).build()).build();
		List<String> validations = new ArrayList<>(Arrays.asList("Tipo AVISTA incompatível com mais de uma parcela"));
		try {
			pagamentoService.salvar(request);
			fail();
		} catch (ValidationException e) {
			assertEquals(validations, e.getValidations());
		}
	}
	
	@Test
	void testMultipleMessages() {
		PagamentoRequest request = PagamentoRequest.builder().transacao(
																TransacaoRequest.builder().cartao(QUINZE_DIGITOS).id(DEZESSEIS_DIGITOS)
																				.descricao(
																						DescricaoRequest.builder()
																						.valor(TEXTO)
																						.dataHora(DateUtils.diaMesAnoHorario(DATA))
																						.estabelecimento("").build())
																				.formaPagamento(
																						FormaPagamentoDTO.builder()
																						.tipo(TEXTO)
																						.parcelas("5.3").build()).build()).build();
		List<String> validations = new ArrayList<>(Arrays.asList("Cartão deve possuir 16 digitos", 
																"ID da Transação não deve ser preenchido ao salvar o pagamento",
																"Valor inválido",
																"Estabelecimento deve ser preenchido",
																"Tipo inválido",
																"Parcelas inválidas"));
		try {
			pagamentoService.salvar(request);
			fail();
		} catch (ValidationException e) {
			assertEquals(validations, e.getValidations());
		}
	}
	
}
