package br.com.solutis.usuario_service.mapper;

import br.com.solutis.usuario_service.dto.usuario.UsuarioRequestDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioResponseDto;
import br.com.solutis.usuario_service.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMapper {
    public Usuario toEntity(UsuarioRequestDto dto){
        Usuario user = new Usuario();

        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setTelefone(dto.getTelefone());
        user.setTipo(dto.getTipo());
        user.setAtivo(dto.getAtivo());

        return user;
    }

    public UsuarioResponseDto toDto(Usuario entity) {
        UsuarioResponseDto dto = new UsuarioResponseDto();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());

        return dto;
    }

    public List<UsuarioResponseDto> toListDto(List<Usuario> users){
        List<UsuarioResponseDto> dtos = new ArrayList<>();

        for(Usuario user : users){
            dtos.add(toDto(user));
        }

        return dtos;
    }
}
