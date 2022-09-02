package com.britto.pagamentoapi.pagamento.service;

import static com.britto.pagamentoapi.pagamento.model.dto.PagamentoRequest.entityOf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

@Service
public class PagamentoService {
	
	@Autowired
	private TransacaoRepository repository;

	public PagamentoResponse consultar(Long id) {
		return PagamentoResponse.of(repository.findById(id)
											.orElseThrow(() -> new ValidationException("Transação não encontrada")));
	}
	
	public Page<PagamentoResponse> consultarTodos(Pageable pageable) {
		return repository.findAll(pageable).map(PagamentoResponse::of);
	}

	public PagamentoResponse estornar(Long id) {
		Transacao transacao = repository.findById(id)
											.orElseThrow(() -> new ValidationException("Transação não encontrada"));
		transacao.getAutorizacao().setStatus(StatusDescricao.CANCELADO);
		return PagamentoResponse.of(repository.save(transacao));
	}
	
	public PagamentoResponse salvar(PagamentoRequest request) {
		validarRequest(request);
		Autorizacao autorizacao = Autorizacao.builder().status(StatusDescricao.AUTORIZADO).build();
		return PagamentoResponse.of(repository.save(entityOf(request, autorizacao)));
	}

	private void validarRequest(PagamentoRequest request) {
		if(request == null) throw new ValidationException("O Request não pode ser nulo");
		validarTransacao(request.getTransacao());
	}

	private void validarTransacao(TransacaoRequest transacao) {
		if(transacao == null) throw new ValidationException("A Transação não pode ser nula");

		List<String> errorList = new ArrayList<>();
		Pattern padraoCartao = Pattern.compile("^\\d{16}$");
		
		if(StringUtils.isBlank(transacao.getCartao())) {
			errorList.add("Cartão deve ser preenchido");
		} else if (!padraoCartao.matcher(transacao.getCartao()).matches()) {
			errorList.add("Cartão deve possuir 16 digitos");
		}

		if(!StringUtils.isBlank(transacao.getId())) {
			errorList.add("ID da Transação não deve ser preenchido ao salvar o pagamento");
		}

		validarDescrica(transacao.getDescricao(), errorList);
		validarFormaPagamento(transacao.getFormaPagamento(), errorList);

		if(!errorList.isEmpty()) {
			throw new ValidationException("Erro de Validação", errorList);
		}
	}

	private void validarDescrica(DescricaoRequest descricao, List<String> errorList) {
		if(descricao == null) throw new ValidationException("A Descrição não pode ser nula");
		
		if(StringUtils.isBlank(descricao.getValor())) {
			errorList.add("Valor deve ser preenchido");
		} else if(!NumberUtils.isCreatable(descricao.getValor())) {
			errorList.add("Valor inválido");
		}

		if(StringUtils.isBlank(descricao.getEstabelecimento())) {
			errorList.add("Estabelecimento deve ser preenchido");
		}
	}

	private void validarFormaPagamento(FormaPagamentoDTO formaPagamento, List<String> errorList) {
		if(formaPagamento == null) throw new ValidationException("A Forma de Pagamento não pode ser nula");
		
		if(StringUtils.isBlank(formaPagamento.getTipo())) {
			errorList.add("Tipo deve ser preenchido");
		} else if(!TipoFormaPagamento.isTipoFormaPagamentoValido(formaPagamento.getTipo())) {
			errorList.add("Tipo inválido");
		}
		
		if(StringUtils.isBlank(formaPagamento.getParcelas())) {
			errorList.add("Parcelas devem ser preenchidas");
		} else if(!NumberUtils.isDigits(formaPagamento.getParcelas())) {
			errorList.add("Parcelas inválidas");
		}
		
		if(TipoFormaPagamento.isTipoFormaPagamentoValido(formaPagamento.getTipo()) 
			&& NumberUtils.isDigits(formaPagamento.getParcelas())
			&& TipoFormaPagamento.AVISTA.getValue().equals(formaPagamento.getTipo())
			& Integer.valueOf(formaPagamento.getParcelas()) != 1) {
			errorList.add("Tipo AVISTA incompatível com mais de uma parcela");
		}
	}
}
