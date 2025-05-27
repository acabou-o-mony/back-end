package br.com.solutis.transacao_service.controller;

import br.com.solutis.transacao_service.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
@AllArgsConstructor
public class TransacaoController {

    @Autowired
    private TransacaoService service;

}
