package br.com.solutis.usuario_service.dto.usuario;

import br.com.solutis.usuario_service.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UsuarioTokenDto {
    private Integer id;
    private String nome;
    private String email;
    private String token;

    public UsuarioTokenDto(Usuario user, String token) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.token = token;
    }

    public UsuarioTokenDto(){}
}
