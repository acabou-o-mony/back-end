package br.com.solutis.produto_service.repository;

import br.com.solutis.produto_service.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByNome(String nome);
    List<Produto> findByAtivoTrue();
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
