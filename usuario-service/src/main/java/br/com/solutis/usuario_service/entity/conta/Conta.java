package br.com.solutis.usuario_service.entity.conta;

import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.usuario_service.entity.cartao.Cartao;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String agencia;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    private Double saldo;

    @Enumerated(EnumType.STRING)
    private Status status;
    private Date data_criacao;

    // Toda interação que o JPA fizer com conta ira alterar diretamente cartão (cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
