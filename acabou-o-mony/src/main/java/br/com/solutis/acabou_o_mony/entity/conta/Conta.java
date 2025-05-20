package br.com.solutis.acabou_o_mony.entity.conta;

import br.com.solutis.acabou_o_mony.entity.Usuario;
import br.com.solutis.acabou_o_mony.entity.cartao.Cartao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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

    @OneToOne
    private Cartao cartao;

    @ManyToOne
    private Usuario usuario;
}
