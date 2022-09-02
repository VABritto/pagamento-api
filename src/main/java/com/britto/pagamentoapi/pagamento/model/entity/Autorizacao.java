package com.britto.pagamentoapi.pagamento.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.britto.pagamentoapi.pagamento.model.enumerator.StatusDescricao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTORIZACAO")
public class Autorizacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CODIGO_AUTORIZACAO", nullable = false)
	private Long codigoAutorizacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private StatusDescricao status;
	
	@OneToOne(mappedBy = "autorizacao", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
	private Transacao transacao;
}
