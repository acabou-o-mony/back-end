package br.com.solutis.transacao_service.mapper;

import br.com.solutis.transacao_service.dto.TransacaoResponseDto;
import br.com.solutis.transacao_service.dto.TransacaoResumedResponseDto;
import br.com.solutis.transacao_service.entity.Transacao;

public class TransacaoMapper {

    public TransacaoResponseDto toResponse(Transacao entity) {
        return (entity == null) ? null : new TransacaoResponseDto(entity.getValor(), entity.getTipo(), entity.getStatus(), entity.getDataHora(), entity.getDescricao(), entity.getContexto(), entity.getCanal());
    }

    public TransacaoResumedResponseDto toResumedResponse(Transacao entity) {
        return (entity == null) ? null : new TransacaoResumedResponseDto(entity.getValor(), entity.getTipo(), entity.getStatus(), entity.getDataHora(), entity.getDescricao(), entity.getCanal());
    }

}
