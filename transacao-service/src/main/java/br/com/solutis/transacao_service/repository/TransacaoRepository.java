package br.com.solutis.transacao_service.repository;

import br.com.solutis.transacao_service.entity.Status;
import br.com.solutis.transacao_service.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findAllByCartaoId(Long id);
    List<Transacao> findAllByCartaoIdAndStatusEquals(Long id, Status status);

}
