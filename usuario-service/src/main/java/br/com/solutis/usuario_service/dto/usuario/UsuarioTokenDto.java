package br.com.solutis.usuario_service.dto.usuario;

import lombok.Data;

@Data
public class UsuarioTokenDto {
    private Integer id;
    private String nome;
    private String email;
    private String token;

}
