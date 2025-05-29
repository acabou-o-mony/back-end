package br.com.solutis.usuario_service.dto.cartao;

import br.com.solutis.usuario_service.entity.cartao.Tipo;
import lombok.Data;

import java.util.Date;

@Data
public class CartaoResponseDto {
    private Date validade;
    private Tipo tipo;
    private Double limite;
}
