package br.com.solutis.acabou_o_mony.entity.pedido;

import br.com.solutis.acabou_o_mony.entity.transacao.Transacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Status status;
    private Double total;
    private Date data_criacao;
    private Date data_pagamento;

    @ManyToOne
    private Transacao transacao;
}
