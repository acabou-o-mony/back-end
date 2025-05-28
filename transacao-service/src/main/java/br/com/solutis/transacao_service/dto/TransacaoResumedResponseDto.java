package br.com.solutis.transacao_service.dto;

import br.com.solutis.transacao_service.entity.Status;
import br.com.solutis.transacao_service.entity.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TransacaoResumedResponseDto {

    private Double valor;
    private Tipo tipo;
    private Status status;
    private LocalDateTime dataHora;
    private String descricao;
    private String canal;


}
