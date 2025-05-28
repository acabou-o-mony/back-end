package br.com.solutis.produto_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record ProdutoRequestDto(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @NotNull
        @PositiveOrZero
        Integer estoque,
        @NotNull
        Boolean ativo,
        @NotNull
        @PastOrPresent
        LocalDate dataCriacao) {
}
