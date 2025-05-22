package br.com.solutis.acabou_o_mony.dto.conta;

import br.com.solutis.acabou_o_mony.entity.conta.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContaRequestDto {

    private String numero;
    private String agencia;
    private Tipo tipo;
    private Double saldo;
    private Integer idUsuario;

}
