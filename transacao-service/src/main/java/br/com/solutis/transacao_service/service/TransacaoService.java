package br.com.solutis.transacao_service.service;

import br.com.solutis.transacao_service.dto.TransacaoRequestDto;
import br.com.solutis.transacao_service.dto.TransacaoResumedResponseDto;
import br.com.solutis.transacao_service.entity.Status;
import br.com.solutis.transacao_service.entity.Transacao;
import br.com.solutis.transacao_service.mapper.TransacaoMapper;
import br.com.solutis.transacao_service.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransacaoService {

    TransacaoMapper mapper;

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private RabbitTemplate template;

    public List<TransacaoResumedResponseDto> listarPorIdCartao(Long id) {
        if (id == null) return null;

        List<TransacaoResumedResponseDto> transacoes = repository.findAllByCartaoId(id).stream().map(mapper::toResumedResponse).toList();

        return (transacoes.isEmpty()) ? null : transacoes;
    }

    public Transacao buscarPorId(Long id) {
        return repository.findById(id).get();
    }

    public Transacao novaTransacao(TransacaoRequestDto req) {
        return repository.save(mapper.toEntity(req));
    }

    public Transacao atualizarTransacao(Long id, String status) {
        if (repository.existsById(id)) {
            Transacao entity = repository.findById(id).get();

            if (status == "PAGO") {
                entity.setStatus(Status.SUCESSO);
                repository.save(entity);
                template.convertAndSend("transacao.confirmada", entity);
                return entity;

            } else {
                entity.setStatus(Status.CANCELADO);

                // TODO: Criar uma validação de cancelamento e retornar algo diferente de null.
                return null;
            }


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

    public List<Transacao> listarSucessosPorId(Long id) {
        return repository.findAllByCartaoIdAndStatusEquals(id, Status.SUCESSO);
    }
}
