package br.com.solutis.transacao_service.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomMessageTrial {

    private String mensagemId;
    private String mensagem;
    private Date dataMensagem;

}
