package br.com.solutis.venda_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrinho;

    private Long produtoId;
    private String nomeProduto;
    private Double precoUnitario;

    private Integer quantidade;

    private Long idConta;

    @ManyToOne
    @JoinColumn(name = "pedido_idpedido")
    private Pedido pedido;
}