package br.com.solutis.transacao_service.service;

import br.com.solutis.transacao_service.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

}
