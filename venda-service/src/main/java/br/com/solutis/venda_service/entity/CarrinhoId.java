package br.com.solutis.venda_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CarrinhoId implements Serializable {

    @Column(name = "id_carrinho")
    private Long idCarrinho;

    @Column(name = "id_produto")
    private Long idProduto;

    public CarrinhoId() {}

    public CarrinhoId(Long idCarrinho, Long idProduto) {
        this.idCarrinho = idCarrinho;
        this.idProduto = idProduto;
    }

    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarrinhoId that = (CarrinhoId) o;
        return Objects.equals(idCarrinho, that.idCarrinho) && Objects.equals(idProduto, that.idProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCarrinho, idProduto);
    }
}

