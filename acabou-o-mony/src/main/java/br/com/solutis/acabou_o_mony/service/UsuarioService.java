package br.com.solutis.acabou_o_mony.service;

import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioRequestDto;
import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioResponseDto;
import br.com.solutis.acabou_o_mony.entity.Usuario;
import br.com.solutis.acabou_o_mony.exception.EntidadeConflituosaException;
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

    public List<UsuarioResponseDto> listar(){
        List<Usuario> dtos = repository.findAll();

        return mapper.toListDto(dtos);
    }

    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto){
        Optional<Usuario> opt = repository.findByEmailIgnoreCase(dto.getEmail());

        if(opt.isPresent()){
            throw new EntidadeConflituosaException("Já há um usuário com esse email cadastrado!");
        }

        Usuario usuario = mapper.toEntity(dto);
        return mapper.toDto(repository.save(usuario));
    }
}
