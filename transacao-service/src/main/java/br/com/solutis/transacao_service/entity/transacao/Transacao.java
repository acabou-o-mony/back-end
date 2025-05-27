package br.com.solutis.transacao_service.entity.transacao;

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
@Table(name = "transacao")
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

    @Column(name = "cartao_id")
    private Integer cartaoId;

}
