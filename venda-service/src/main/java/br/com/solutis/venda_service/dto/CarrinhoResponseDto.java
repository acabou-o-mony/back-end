package br.com.solutis.venda_service.dto;

public record CarrinhoResponseDto(
        Long idCarrinho,
        Long produtoId,
        String nomeProduto,
        Integer quantidade,
        Double precoUnitario,
        Double subtotal
) {

}