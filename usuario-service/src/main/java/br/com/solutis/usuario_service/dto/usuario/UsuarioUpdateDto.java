package br.com.solutis.usuario_service.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioUpdateDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String senha;
    @NotBlank
    private String telefone;
    @NotBlank
    private String tipo;
    @NotNull
    private Boolean ativo;
}
