package br.com.solutis.usuario_service.repository;

import br.com.solutis.usuario_service.entity.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
}
