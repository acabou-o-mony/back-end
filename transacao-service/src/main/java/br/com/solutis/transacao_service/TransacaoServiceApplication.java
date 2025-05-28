package br.com.solutis.transacao_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.solutis.transacao_service", "br.com.solutis.usuario_service", "br.com.solutis.venda_service"})
public class TransacaoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransacaoServiceApplication.class, args);
	}

}
