package br.com.solutis.acabou_o_mony.entity.conta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numero;
    private String agencia;
    private Tipo tipo;
    private Double saldo;
    private Status status;
    private Date data_criacao;
}
