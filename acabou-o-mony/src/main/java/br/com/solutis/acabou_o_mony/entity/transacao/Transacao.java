package br.com.solutis.acabou_o_mony.entity.transacao;

import br.com.solutis.acabou_o_mony.entity.cartao.Cartao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal valor;
    private Tipo tipo;
    private Status status;
    private LocalDateTime data_hora;
    private String descricao;
    private String contexto;
    private String canal;

    @ManyToOne
    private Cartao cartao;
}
