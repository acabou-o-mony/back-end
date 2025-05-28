package br.com.solutis.produto_service.repository;

import br.com.solutis.produto_service.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
