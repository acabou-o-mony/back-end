package br.com.solutis.usuario_service.service;

import br.com.solutis.usuario_service.dto.conta.ContaRequestDto;
import br.com.solutis.usuario_service.dto.conta.ContaResponseDto;
import br.com.solutis.usuario_service.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {
    private

    @Autowired
    private ContaRepository repository;

    // CREATE
    public ContaResponseDto criar(ContaRequestDto dto){
        repository.save();
        return null;
    }
}
