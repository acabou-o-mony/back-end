package br.com.solutis.usuario_service.dto.conta;

import br.com.solutis.usuario_service.dto.cartao.CartaoResponseDto;
import lombok.Data;

import java.util.Date;

@Data
public class ContaResponseDto {
    private Integer id;
    private String numero;
    private String agencia;
    private Double saldo;
    private Date data_criacao;
    private CartaoResponseDto cartao;
}
