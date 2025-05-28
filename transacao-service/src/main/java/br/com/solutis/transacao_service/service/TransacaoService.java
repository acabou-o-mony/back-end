package br.com.solutis.transacao_service.service;

import br.com.solutis.transacao_service.dto.TransacaoResumedResponseDto;
import br.com.solutis.transacao_service.mapper.TransacaoMapper;
import br.com.solutis.transacao_service.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransacaoService {

    TransacaoMapper mapper;

    @Autowired
    private TransacaoRepository repository;

    public List<TransacaoResumedResponseDto> listarPorIdCartao(Long id) {
        if (id == null) return null;

        List<TransacaoResumedResponseDto> transacoes = repository.findAllByCartaoId(id).stream().map(mapper::toResumedResponse).toList();

        return (transacoes.isEmpty()) ? null : transacoes;
    }

}
