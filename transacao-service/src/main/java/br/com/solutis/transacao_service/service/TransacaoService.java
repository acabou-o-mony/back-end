package br.com.solutis.transacao_service.service;

import br.com.solutis.transacao_service.dto.TransacaoRequestDto;
import br.com.solutis.transacao_service.dto.TransacaoResumedResponseDto;
import br.com.solutis.transacao_service.entity.Status;
import br.com.solutis.transacao_service.entity.Transacao;
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

    public Transacao novaTransacao(TransacaoRequestDto req) {
        return repository.save(mapper.toEntity(req));
    }

    public Transacao atualizarTransacao(Long id, boolean paga) {
        if (repository.existsById(id)) {
            Transacao entity = repository.findById(id).get();

            if (paga) {
                entity.setStatus(Status.SUCESSO);
            } else {
                entity.setStatus(Status.CANCELADO);
            }

            return repository.save(entity);

        } else {
            return null;
        }
    }

    public List<Transacao> listarPendentesPorId(Long id) {
        return repository.findAllByCartaoIdAndStatusEquals(id, Status.PENDENTE);
    }

    public List<Transacao> listarFalhasPorId(Long id) {
        return repository.findAllByCartaoIdAndStatusEquals(id, Status.CANCELADO);
    }

}
