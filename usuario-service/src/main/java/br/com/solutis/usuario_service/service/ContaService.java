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
}
