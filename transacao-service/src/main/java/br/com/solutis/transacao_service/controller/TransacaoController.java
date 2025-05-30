package br.com.solutis.transacao_service.controller;

import br.com.solutis.transacao_service.dto.TransacaoRequestDto;
import br.com.solutis.transacao_service.dto.TransacaoResponseDto;
import br.com.solutis.transacao_service.dto.TransacaoResumedResponseDto;
import br.com.solutis.transacao_service.entity.Transacao;
import br.com.solutis.transacao_service.mapper.TransacaoMapper;
import br.com.solutis.transacao_service.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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
        String url = "http://localhost:8085/pedidos/" + req.getPedidoId();
        Object pedido =template.getForObject(url, Object.class);
        return ResponseEntity.status(201).body(mapper.toResponse(service.novaTransacao(req)));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<TransacaoResponseDto> atualizarTransacao(@PathVariable Long id) {
        if (id == null) return ResponseEntity.status(400).build();

        Transacao entity = service.buscarPorId(id);

        String url = "http://localhost:8085/pedidos/" + entity.getPedidoId();
        Map<String, Object> pedido = template.getForObject(url, Map.class);
        String status = (String) pedido.get("status");

        entity = service.atualizarTransacao(id, status);

        return (entity == null) ? ResponseEntity.status(404).build() : ResponseEntity.status(200).body(mapper.toResponse(entity));
    }

    @GetMapping("/pendentes/{id}")
    public ResponseEntity<List<TransacaoResumedResponseDto>> listarPendentesPorId(@PathVariable Long id) {
        List<Transacao> lista = service.listarPendentesPorId(id);

        return (lista.isEmpty()) ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(lista.stream().map(mapper::toResumedResponse).toList());
    }

    @GetMapping("/falhas/{id}")
    public ResponseEntity<List<TransacaoResumedResponseDto>> listarFalhasPorId(@PathVariable Long id) {
        List<Transacao> lista = service.listarPendentesPorId(id);

        return (lista.isEmpty()) ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(lista.stream().map(mapper::toResumedResponse).toList());
    }

    @GetMapping("/sucessos/{id}")
    public ResponseEntity<List<TransacaoResumedResponseDto>> listarSucessosPorId(@PathVariable Long id) {
        List<Transacao> lista = service.listarSucessosPorId(id);

        return (lista.isEmpty()) ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(lista.stream().map(mapper::toResumedResponse).toList());
    }
}
