package br.com.solutis.venda_service.mapper;

import br.com.solutis.venda_service.dto.CarrinhoRequestDto;
import br.com.solutis.venda_service.entity.Carrinho;

public class CarrinhoMapper {

    public static Carrinho toEntity(CarrinhoRequestDto dto, Double precoUnitario) {
        Carrinho carrinho = new Carrinho();

        carrinho.setProdutoId(dto.produtoId());
        carrinho.setQuantidade(dto.quantidade());
        carrinho.setIdConta(dto.idConta());
        carrinho.setPrecoUnitario(precoUnitario);

        return carrinho;
    }
}
