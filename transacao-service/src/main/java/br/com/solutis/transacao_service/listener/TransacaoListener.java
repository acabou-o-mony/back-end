package br.com.solutis.transacao_service.listener;

import br.com.solutis.transacao_service.entity.Transacao;
import br.com.solutis.transacao_service.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransacaoListener {

    @Autowired
    private EmailService service;

    @RabbitListener(queues = "transacao.confirmada")
    public void ouvirTransacaoConfirmada(Transacao entity) {
        System.out.println("🔔 Transacao paga com sucesso de valor R$" + entity.getValor());
        service.enviarEmailConfirmacao(entity);
    }

    @RabbitListener(queues = "transacao.cancelada")
    public void ouvirTransacaoCancelada(Transacao entity) {
        System.out.println("🔔 Transacao de valor R$" + entity.getValor() + " cancelada.");
        service.enviarEmailCancelamento(entity);
    }

}
