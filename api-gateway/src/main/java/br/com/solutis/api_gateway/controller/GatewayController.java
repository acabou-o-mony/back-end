package br.com.solutis.api_gateway.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class GatewayController {

    private final WebClient client;

    public GatewayController() {
        this.client = WebClient.builder().build();
    }

    @RequestMapping("/{service}/{path:^(?!api).*$}/**")
    public Mono<ResponseEntity<String>> proxy(
            @PathVariable String service,
            @PathVariable String path,
            @RequestHeader HttpHeaders headers,
            @RequestParam(required = false) MultiValueMap<String, String> queryParams,
            @RequestBody(required = false) Mono<String> body,
            ServerHttpRequest request
            ) {

        String baseUrl = switch (service) {
            case "contas", "usuarios" -> "http://localhost:8080";
            case "produtos" -> "http://localhost:8081";
            case "pedidos", "carrinhos" -> "http://localhost:8085";
            case "transacoes" -> "http://localhost:8083";
            default -> null;
        };

        if (baseUrl == null) return Mono.just(ResponseEntity.status(400).body("Serviço não encontrado."));

        String fullPath = request.getURI().getRawPath().replace("/api/" + service, "");

        return client.method(request.getMethod())
                .uri(baseUrl + fullPath)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(body == null ? Mono.empty() : body, String.class)
                .retrieve()
                .toEntity(String.class);
    }

}
