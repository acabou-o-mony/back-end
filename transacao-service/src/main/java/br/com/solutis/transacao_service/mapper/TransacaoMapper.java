package br.com.solutis.transacao_service.mapper;

import br.com.solutis.transacao_service.dto.transacao.TransacaoResponseDto;
import br.com.solutis.transacao_service.dto.transacao.TransacaoResumedResponseDto;
import br.com.solutis.transacao_service.entity.transacao.Transacao;

public class TransacaoMapper {

    public TransacaoResponseDto toResponse(Transacao entity) {
        return (entity == null) ? null : new TransacaoResponseDto(entity.getValor(), entity.getTipo(), entity.getStatus(), entity.getDataHora(), entity.getDescricao(), entity.getContexto(), entity.getCanal());
    }

    public TransacaoResumedResponseDto toResumedResponse(Transacao entity) {
        return (entity == null) ? null : new TransacaoResumedResponseDto(entity.getValor(), entity.getTipo(), entity.getStatus(), entity.getDataHora(), entity.getDescricao(), entity.getCanal());
    }

}
