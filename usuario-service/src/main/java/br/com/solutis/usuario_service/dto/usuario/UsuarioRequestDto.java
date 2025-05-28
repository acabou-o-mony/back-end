package br.com.solutis.usuario_service.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDto {
    @NotBlank
    private String nome;
    @Email
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    private String telefone;
    @NotBlank
    private String tipo;
    @NotNull
    private Boolean ativo;
}
