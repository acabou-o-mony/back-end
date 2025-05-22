package br.com.solutis.acabou_o_mony.mapper;

import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioRequestDto;
import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioResponseDto;
import br.com.solutis.acabou_o_mony.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMapper {
    public Usuario toEntity(UsuarioRequestDto dto){
        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTelefone(dto.getTelefone());
        usuario.setAtivo(dto.getAtivo());

        return usuario;
    }

    public UsuarioResponseDto toDto(Usuario usuario){
        UsuarioResponseDto dto = new UsuarioResponseDto();

        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());

        return dto;
    }

    public List<UsuarioResponseDto> toListDto(List<Usuario> usuarios){
        List<UsuarioResponseDto> dtos = new ArrayList<>();

        for (Usuario user : usuarios){
            dtos.add(toDto(user));
        }

        return dtos;
    }
}
