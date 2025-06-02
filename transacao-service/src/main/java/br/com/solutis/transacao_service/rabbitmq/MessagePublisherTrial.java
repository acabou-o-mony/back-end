package br.com.solutis.transacao_service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class MessagePublisherTrial {

    @Autowired
    private RabbitTemplate template;

    @PostMapping("/publicar")
    public String publishMessage(@RequestBody CustomMessageTrial message) {
        message.setMensagemId(UUID.randomUUID().toString());
        message.setDataMensagem(new Date());

        template.convertAndSend(MqConfig.EXCHANGE, MqConfig.ROUTING_KEY, message);

        return "Mensagem publicada.";
    }

}
