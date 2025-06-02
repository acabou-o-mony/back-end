package br.com.solutis.transacao_service.rabbitmq;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receberMensagem(String mensagem) {
        System.out.println("Recebida <" + mensagem + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
