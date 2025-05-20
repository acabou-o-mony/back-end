package br.com.solutis.acabou_o_mony.entity.cartao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numero;
    private Date validade;
    private String cvv;
    private Tipo tipo;
    private String banco;
    private Double limite;
}
