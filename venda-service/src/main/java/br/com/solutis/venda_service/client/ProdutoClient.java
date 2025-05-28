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
        String url = "http://localhost:8081/produtos/" + produtoId;
        ProdutoResponseDto produto = restTemplate.getForObject(url, ProdutoResponseDto.class);

        if (produto == null) {
            System.out.println("Produto não encontrado para o ID: " + produtoId);
        } else {
            System.out.println("Produto encontrado: " + produto);
        }

        return produto;
    }
}