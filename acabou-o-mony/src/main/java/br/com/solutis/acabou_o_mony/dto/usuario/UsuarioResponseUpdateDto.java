package br.com.solutis.acabou_o_mony.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioResponseUpdateDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String telefone;
    @NotBlank
    private String tipo;
    @NotNull
    private Boolean ativo;
}
