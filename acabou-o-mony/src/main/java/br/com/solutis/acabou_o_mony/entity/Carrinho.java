package br.com.solutis.acabou_o_mony.entity;

import br.com.solutis.acabou_o_mony.entity.pedido.Pedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produto_idproduto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "pedido_idpedido")
    private Pedido pedido;

    private Integer quantidade;
    private Double preco_unitario;
}
