package br.com.solutis.transacao_service.mapper;

import br.com.solutis.transacao_service.dto.TransacaoRequestDto;
import br.com.solutis.transacao_service.dto.TransacaoResponseDto;
import br.com.solutis.transacao_service.dto.TransacaoResumedResponseDto;
import br.com.solutis.transacao_service.entity.Status;
import br.com.solutis.transacao_service.entity.Transacao;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransacaoMapper {

    public Transacao toEntity(TransacaoRequestDto req) {
        return (req == null) ? null : new Transacao(null, req.getValor(), req.getTipo(), Status.PENDENTE, LocalDateTime.now(), req.getDescricao(), req.getContexto(), req.getCanal(), req.getCartaoId(), req.getPedidoId());
    }

    public TransacaoResponseDto toResponse(Transacao entity) {
        return (entity == null) ? null : new TransacaoResponseDto(entity.getValor(), entity.getTipo(), entity.getStatus(), entity.getDataHora(), entity.getDescricao(), entity.getContexto(), entity.getCanal());
    }

    public TransacaoResumedResponseDto toResumedResponse(Transacao entity) {
        return (entity == null) ? null : new TransacaoResumedResponseDto(entity.getValor(), entity.getTipo(), entity.getStatus(), entity.getDataHora(), entity.getDescricao(), entity.getCanal());
    }

}
