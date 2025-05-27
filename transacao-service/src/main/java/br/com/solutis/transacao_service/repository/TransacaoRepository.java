package br.com.solutis.transacao_service.repository;

import br.com.solutis.transacao_service.entity.transacao.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {



}
