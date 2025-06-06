package br.com.solutis.acabou_o_mony.entity.transacao;

import br.com.solutis.acabou_o_mony.entity.cartao.Cartao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal valor;
    private Tipo tipo;
    private Status status;
    private LocalDateTime dataHora;
    private String descricao;
    private String contexto;
    private String canal;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;
}
