package br.com.solutis.transacao_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
		"br.com.solutis.transacao_service.entity",
		"br.com.solutis.usuario_service.entity",
		"br.com.solutis.usuario_service.entity.conta",
		"br.com.solutis.usuario_service.entity.cartao",
		"br.com.solutis.venda_service.entity"
})
@EnableJpaRepositories(basePackages = {
		"br.com.solutis.transacao_service.repository",
		"br.com.solutis.usuario_service.repository",
		"br.com.solutis.venda_service.repository"
})
public class TransacaoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransacaoServiceApplication.class, args);
	}

}
