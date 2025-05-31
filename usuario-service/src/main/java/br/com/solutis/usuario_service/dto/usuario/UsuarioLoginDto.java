package br.com.solutis.usuario_service.dto.usuario;

import lombok.Data;

@Data
public class UsuarioLoginDto {
    private String email;
    private String senha;
}
