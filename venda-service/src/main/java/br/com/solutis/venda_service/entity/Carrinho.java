package br.com.solutis.venda_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Carrinho {

    @EmbeddedId
    private CarrinhoId carrinhoId;

    private Double precoUnitario;
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "pedido_idpedido")
    private Pedido pedido;
}