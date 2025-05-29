package br.com.solutis.venda_service.dto;

public record CarrinhoRequestDto(Long idCarrinho, Long produtoId, Integer quantidade) {
}