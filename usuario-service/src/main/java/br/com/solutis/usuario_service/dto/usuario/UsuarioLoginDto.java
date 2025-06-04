package br.com.solutis.usuario_service.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UsuarioLoginDto {
    private String email;
    private String senha;
}
