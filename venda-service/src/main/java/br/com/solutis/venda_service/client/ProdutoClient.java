package br.com.solutis.venda_service.client;

import br.com.solutis.venda_service.dto.ProdutoResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProdutoClient {

    private final RestTemplate restTemplate;

    public ProdutoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProdutoResponseDto buscarProdutoPorId(Long produtoId) {
        String url = "http://localhost:8082/produtos/" + produtoId;
        return restTemplate.getForObject(url, ProdutoResponseDto.class);
    }
}