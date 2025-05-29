package br.com.solutis.venda_service.mapper;

import br.com.solutis.venda_service.dto.CarrinhoRequestDto;
import br.com.solutis.venda_service.entity.Carrinho;
import br.com.solutis.venda_service.entity.CarrinhoId;

public class CarrinhoMapper {

    public static Carrinho toEntity(CarrinhoRequestDto dto, Double precoUnitario) {
        Carrinho carrinho = new Carrinho();

        CarrinhoId carrinhoId = new CarrinhoId(dto.idCarrinho(), dto.produtoId());

        carrinho.setCarrinhoId(carrinhoId);
        carrinho.setPrecoUnitario(precoUnitario);
        carrinho.setQuantidade(dto.quantidade());

        return carrinho;
    }
}
