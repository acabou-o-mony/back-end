package br.com.solutis.transacao_service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListenerTrial {

    @RabbitListener(queues = MqConfig.QUEUE)
    public void listener(CustomMessageTrial message) {
        System.out.println(message);
    }

}
