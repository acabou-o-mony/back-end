package br.com.solutis.acabou_o_mony.dto.usuario;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioResponseDto {
    private Integer id;
    private String nome;
    private String email;
}
