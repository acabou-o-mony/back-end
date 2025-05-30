package br.com.solutis.usuario_service.service;

import br.com.solutis.usuario_service.dto.conta.ContaRequestDto;
import br.com.solutis.usuario_service.dto.conta.ContaResponseDto;
import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.usuario_service.entity.conta.Conta;
import br.com.solutis.usuario_service.exceptions.EntidadeNaoEncontradaException;
import br.com.solutis.usuario_service.mapper.ContaMapper;
import br.com.solutis.usuario_service.repository.ContaRepository;
import br.com.solutis.usuario_service.repository.UsuarioRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {
    private ContaMapper mapper = new ContaMapper();

    @Autowired
    private ContaRepository repositoryConta;

    @Autowired
    private UsuarioRespository respositoryUser;

    // CREATE
    public ContaResponseDto criar(ContaRequestDto dto){
        Usuario user = respositoryUser.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com id %d não encontrado!".formatted(dto.getUsuarioId())));

        Conta conta = mapper.toEntity(dto);
        conta.setUsuario(user);

        return mapper.toDto(repositoryConta.save(conta));
    }

    // READ
    public List<ContaResponseDto> listar(){
        List<ContaResponseDto> dtos = mapper.toListDto(repositoryConta.findAll());

        return dtos;
    }

    public ContaResponseDto listarPorId(Integer id){
        Conta conta = repositoryConta.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta com id %d não encontrada!".formatted(id)));

        return mapper.toDto(conta);
    }

    // UPDATE
    public ContaResponseDto atualizar(Integer id, ContaRequestDto dto){
        Conta conta = repositoryConta.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta com id %d não encontrada!".formatted(id)));

        conta.setId(id);
        conta.setNumero(dto.getNumero());
        conta.setAgencia(dto.getAgencia());
        conta.setTipo(dto.getTipo());
        conta.setSaldo(dto.getSaldo());
        conta.setStatus(dto.getStatus());

        return mapper.toDto(repositoryConta.save(conta));
    }

    // Delete
    public void deletar(Integer id){
        if(!repositoryConta.existsById(id)){
            throw new EntidadeNaoEncontradaException("Conta com id %d não encontrada!".formatted(id));
        }

        repositoryConta.deleteById(id);
    }
}
