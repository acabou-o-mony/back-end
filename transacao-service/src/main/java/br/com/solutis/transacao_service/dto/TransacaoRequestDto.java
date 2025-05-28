package br.com.solutis.transacao_service.dto;

import br.com.solutis.transacao_service.entity.Tipo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransacaoRequestDto {

    private Double valor;
    private Tipo tipo;
    private String descricao;
    private String contexto;
    private String canal;
    private Long cartaoId;
    private Long pedidoId;

}
