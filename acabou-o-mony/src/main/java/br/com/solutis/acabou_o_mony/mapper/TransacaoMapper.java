package br.com.solutis.acabou_o_mony.mapper;

import br.com.solutis.acabou_o_mony.dto.transacao.TransacaoResponseDto;
import br.com.solutis.acabou_o_mony.dto.transacao.TransacaoResumedResponseDto;
import br.com.solutis.acabou_o_mony.entity.transacao.Transacao;

public class TransacaoMapper {

    public TransacaoResponseDto toResponse(Transacao entity) {
        return (entity == null) ? null : new TransacaoResponseDto(entity.getValor(), entity.getTipo(), entity.getStatus(), entity.getDataHora(), entity.getDescricao(), entity.getContexto(), entity.getCanal());
    }

    public TransacaoResumedResponseDto toResumedResponse(Transacao entity) {
        return (entity == null) ? null : new TransacaoResumedResponseDto(entity.getValor(), entity.getTipo(), entity.getStatus(), entity.getDataHora(), entity.getDescricao(), entity.getCanal());
    }

}
