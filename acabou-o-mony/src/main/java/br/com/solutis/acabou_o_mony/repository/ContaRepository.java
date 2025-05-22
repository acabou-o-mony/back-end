package br.com.solutis.acabou_o_mony.repository;

import br.com.solutis.acabou_o_mony.entity.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

    boolean existsByCartao_Id(Integer id);
    Conta findByCartao_Id(Integer id);

}
