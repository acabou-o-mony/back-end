package br.com.solutis.transacao_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double valor;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dataHora;
    private String descricao;
    private String contexto;
    private String canal;

    @Column(name = "cartao_id")
    private Long cartaoId;

    @Column(name = "pedido_id")
    private Long pedidoId;

}
