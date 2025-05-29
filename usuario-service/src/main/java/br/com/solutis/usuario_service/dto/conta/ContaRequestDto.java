package br.com.solutis.usuario_service.dto.conta;

import br.com.solutis.usuario_service.entity.cartao.Cartao;
import br.com.solutis.usuario_service.entity.conta.Status;
import br.com.solutis.usuario_service.entity.conta.Tipo;
import lombok.Data;

@Data
public class ContaRequestDto {
    private String numero;
    private String agencia;
    private Tipo tipo;
    private Double saldo;
    private Status status;
    private Cartao cartao;
}
