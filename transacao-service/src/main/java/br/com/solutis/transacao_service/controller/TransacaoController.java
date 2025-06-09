package br.com.solutis.transacao_service.controller;

import br.com.solutis.transacao_service.dto.TransacaoRequestDto;
import br.com.solutis.transacao_service.dto.TransacaoResponseDto;
import br.com.solutis.transacao_service.dto.TransacaoResumedResponseDto;
import br.com.solutis.transacao_service.entity.Transacao;
import br.com.solutis.transacao_service.mapper.TransacaoMapper;
import br.com.solutis.transacao_service.service.TransacaoService;
import br.com.solutis.venda_service.entity.Pedido;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
@AllArgsConstructor
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @Autowired
    private TransacaoMapper mapper;

    private final WebClient pedidoWebClient;

    @Autowired
    public TransacaoController(WebClient.Builder webClientBuilder) {
        this.pedidoWebClient = webClientBuilder.baseUrl("http://localhost:8085").build();
    }

    @PostMapping
    public ResponseEntity<TransacaoResponseDto> novaTransacao(@RequestBody TransacaoRequestDto req) {
        String url = "/pedidos/" + req.getPedidoId();
        Pedido pedido = pedidoWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Pedido.class)
                .block();
        return ResponseEntity.status(201).body(mapper.toResponse(service.novaTransacao(req)));
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<TransacaoResponseDto> atualizarTransacao(@PathVariable Long id, boolean isPago) {
        if (id == null) return ResponseEntity.status(400).build();

        Transacao entity = service.buscarPorId(id);
        if (entity == null) return ResponseEntity.status(404).build();

        if (isPago) {
            entity = service.atualizarTransacao(id, isPago);

            String url = "/pedidos/status/" + entity.getPedidoId() + "?status=PAGO";
            Pedido pedido = pedidoWebClient.patch()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(Pedido.class)
                    .block();

            System.out.println("Pedido de id " + entity.getPedidoId() + " confirmado!");

            return ResponseEntity.status(200).body(mapper.toResponse(entity));
        } else {
            entity = service.atualizarTransacao(id, isPago);

            String url = "/pedidos/status/" + entity.getPedidoId() + "?status=CANCELADO";
            Pedido pedido = pedidoWebClient.patch()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(Pedido.class)
                    .block();

            System.out.println("Pedido de id " + entity.getPedidoId() + " cancelado.");

            return ResponseEntity.status(200).body(mapper.toResponse(entity));
        }

    }

    @GetMapping("/pendentes/{id}")
    public ResponseEntity<List<TransacaoResumedResponseDto>> listarPendentesPorId(@PathVariable Long id) {
        List<Transacao> lista = service.listarPendentesPorId(id);

        return (lista.isEmpty()) ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(lista.stream().map(mapper::toResumedResponse).toList());
    }

    @GetMapping("/falhas/{id}")
    public ResponseEntity<List<TransacaoResumedResponseDto>> listarFalhasPorId(@PathVariable Long id) {
        List<Transacao> lista = service.listarFalhasPorId(id);

        return (lista.isEmpty()) ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(lista.stream().map(mapper::toResumedResponse).toList());
    }

    @GetMapping("/sucessos/{id}")
    public ResponseEntity<List<TransacaoResumedResponseDto>> listarSucessosPorId(@PathVariable Long id) {
        List<Transacao> lista = service.listarSucessosPorId(id);

        return (lista.isEmpty()) ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(lista.stream().map(mapper::toResumedResponse).toList());
    }
}
