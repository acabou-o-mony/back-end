package dto;

import java.time.LocalDate;

public record ProdutoResponseDto(Long id,
        String nome,
        String descricao,
        Integer estoque,
        Boolean ativo,
        LocalDate dataCriacao) {
}
