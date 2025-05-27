package br.com.solutis.usuario_microservice.entity.conta;

import br.com.solutis.usuario_microservice.entity.Usuario;
import br.com.solutis.usuario_microservice.entity.cartao.Cartao;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
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
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
