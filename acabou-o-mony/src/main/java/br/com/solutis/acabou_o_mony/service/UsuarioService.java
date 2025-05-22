package br.com.solutis.acabou_o_mony.service;

import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioRequestDto;
import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioResponseDto;
import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioResponseUpdateDto;
import br.com.solutis.acabou_o_mony.entity.Usuario;
import br.com.solutis.acabou_o_mony.exception.EntidadeConflituosaException;
import br.com.solutis.acabou_o_mony.exception.EntitadeNaoEncontradaException;
import br.com.solutis.acabou_o_mony.mapper.UsuarioMapper;
import br.com.solutis.acabou_o_mony.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    UsuarioMapper mapper = new UsuarioMapper();
    @Autowired
    private UsuarioRepository repository;

    // CREATE
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto){
        Optional<Usuario> opt = repository.findByEmailIgnoreCase(dto.getEmail());

        if(opt.isPresent()){
            throw new EntidadeConflituosaException("Já há um usuário com esse email cadastrado!");
        }

        Usuario usuario = mapper.toEntity(dto);
        return mapper.toDto(repository.save(usuario));
    }




    // READ
    public List<UsuarioResponseDto> listar(){
        List<Usuario> dtos = repository.findAll();

        return mapper.toListDto(dtos);
    }

    public UsuarioResponseDto listarPorId(Integer id){
        Optional<Usuario> user = repository.findById(id);

        if(user.isEmpty()){
            throw new EntitadeNaoEncontradaException("Usuário com id %d não encontrado".formatted(id));
        }

        return mapper.toDto(user.get());
    }




    // UPDATE
    public UsuarioResponseUpdateDto atualizar(Integer id, UsuarioRequestDto dto){
        Usuario userAt = repository.findById(id).
                orElseThrow(() -> new EntitadeNaoEncontradaException("Usuário com id %d não encontrado!".formatted(id)));

        userAt.setId(id);
        userAt.setNome(dto.getNome());
        userAt.setTelefone(dto.getTelefone());
        userAt.setTipo(dto.getTipo());
        userAt.setAtivo(dto.getAtivo());

        return mapper.toUpdateDto(repository.save(userAt));
    }
}
