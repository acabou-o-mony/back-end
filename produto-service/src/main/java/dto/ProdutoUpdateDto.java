package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record ProdutoUpdateDto(
        @PositiveOrZero
        Integer estoque,
        @NotNull
        Boolean ativo
) {
}
