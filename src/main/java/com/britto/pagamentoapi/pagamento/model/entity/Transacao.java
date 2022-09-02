package com.britto.pagamentoapi.pagamento.model.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.britto.pagamentoapi.pagamento.model.enumerator.TipoFormaPagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TRANSACAO")
public class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CARTAO", nullable = false)
	private String cartao;
	
	@Column(name = "VALOR", nullable = false)
	private Double valor;
	
	@Column(name = "DATA_HORA", nullable = false)
	private LocalDateTime dataHora;
	
	@Column(name = "ESTABELECIMENTO", nullable = false)
	private String estabelecimento;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CODIGO_AUTORIZACAO", nullable = false)
	private Autorizacao autorizacao;
	
	@Column(name = "NSU", nullable = false)
	private String nsu;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", nullable = false)
	private TipoFormaPagamento tipo;
	
	@Column(name = "PARCELAS", nullable = false)
	private Integer parcelas;
}
