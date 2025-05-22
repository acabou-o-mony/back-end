package br.com.solutis.acabou_o_mony.repository;

import br.com.solutis.acabou_o_mony.entity.transacao.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    List<Transacao> findAllByCartao_Id(Integer id);

}
