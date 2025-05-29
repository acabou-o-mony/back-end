package br.com.solutis.usuario_service.entity.cartao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Date;

@Data
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
}
