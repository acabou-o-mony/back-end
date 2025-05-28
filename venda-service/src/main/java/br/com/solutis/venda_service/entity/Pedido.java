package br.com.solutis.venda_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpedido;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double total;
    private Date dataCriacao;
    private Date dataPagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Carrinho> itens;

    private Long idConta;
}