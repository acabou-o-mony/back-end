package br.com.solutis.acabou_o_mony.dto.transacao;

import br.com.solutis.acabou_o_mony.entity.transacao.Status;
import br.com.solutis.acabou_o_mony.entity.transacao.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransacaoResumedResponseDto {

    private BigDecimal valor;
    private Tipo tipo;
    private Status status;
    private LocalDateTime dataHora;
    private String descricao;
    private String canal;

}
