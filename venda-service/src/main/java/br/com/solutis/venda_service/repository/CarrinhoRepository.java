package br.com.solutis.venda_service.repository;

import br.com.solutis.venda_service.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer> {

    List<Carrinho> findByCarrinhoId_IdCarrinho(Long idCarrinho);

    List<Carrinho> findByCarrinhoId_IdCarrinhoAndCarrinhoId_IdProduto(Long idCarrinho, Long idProduto);
}
