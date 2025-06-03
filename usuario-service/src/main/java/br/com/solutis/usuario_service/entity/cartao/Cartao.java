package br.com.solutis.usuario_service.entity.cartao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String numero;
    @NotNull
    private Date validade;
    @NotBlank
    private String cvv;
    @NotNull
    private Tipo tipo;
    @NotBlank
    private String banco;
    @Positive
    private Double limite;

    public Cartao(Integer id, String numero, Date validade, String cvv, Tipo tipo, String banco, Double limite) {
        this.id = id;
        this.numero = numero;
        this.validade = validade;
        this.cvv = cvv;
        this.tipo = tipo;
        this.banco = banco;
        this.limite = limite;
    }
}
