package br.com.solutis.usuario_service.dto.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDto {
    private Integer id;
    private String nome;
    private String email;
}
