package com.britto.pagamentoapi.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.britto.pagamentoapi.pagamento.model.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
