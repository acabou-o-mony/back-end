package br.com.solutis.usuario_service.service;

import br.com.solutis.usuario_service.dto.usuario.UsuarioRequestDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioResponseDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioUpdateDto;
import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.usuario_service.exceptions.EntidadeNaoEncontradaException;
import br.com.solutis.usuario_service.mapper.UsuarioMapper;
import br.com.solutis.usuario_service.repository.UsuarioRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private UsuarioMapper mapper = new UsuarioMapper();

    @Autowired
    private UsuarioRespository respository;

    // CREATE
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto){
        Usuario user = mapper.toEntity(dto);
        return mapper.toDto(respository.save(user));
    }

    // READ
    public List<UsuarioResponseDto> listar(){
        return mapper.toListDto(respository.findAll());
    }

    public UsuarioResponseDto listarPorId(Integer id){
        Usuario entity = respository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com id %d não encontrado".formatted(id)));

        return mapper.toDto(entity);
    }

    // UPDATE
    public UsuarioResponseDto atualizar(Integer id, UsuarioUpdateDto request){
        Usuario user = respository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário para atualização com id %d não encontrado".formatted(id)));

        user.setId(id);
        user.setNome(request.getNome());
        user.setSenha(request.getSenha());
        user.setTelefone(request.getTelefone());
        user.setTipo(request.getTipo());
        user.setAtivo(request.getAtivo());

        return mapper.toDto(respository.save(user));
    }

    // DELETE
    public void deletarPorId(Integer id){
        if(!respository.existsById(id)){
            throw new EntidadeNaoEncontradaException("Usuário para deleção com id %d não encontrado".formatted(id));
        }

        respository.deleteById(id);
    }
}
