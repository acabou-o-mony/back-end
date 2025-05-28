package br.com.solutis.venda_service.dto;

public record CarrinhoRequestDto(Long produtoId, Integer quantidade, Long idConta) {
}