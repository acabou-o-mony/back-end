package br.com.solutis.transacao_service.controller;

import br.com.solutis.transacao_service.dto.TransacaoRequestDto;
import br.com.solutis.transacao_service.dto.TransacaoResponseDto;
import br.com.solutis.transacao_service.mapper.TransacaoMapper;
import br.com.solutis.transacao_service.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/transacoes")
@AllArgsConstructor
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    private TransacaoMapper mapper;

    private final RestTemplate template = new RestTemplate();

    @PostMapping
    public ResponseEntity<TransacaoResponseDto> novaTransacao(@RequestBody TransacaoRequestDto req) {
        String url = "http://localhost:8085/carrinho/" + req.getPedidoId();
        Object pedido =template.getForObject(url, Object.class);
        return ResponseEntity.status(201).body(mapper.toResponse(service.novaTransacao(req)));
    }

}
