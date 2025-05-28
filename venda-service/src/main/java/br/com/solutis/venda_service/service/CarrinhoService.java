package br.com.solutis.venda_service.service;

import br.com.solutis.venda_service.client.ProdutoClient;
import br.com.solutis.venda_service.dto.CarrinhoRequestDto;
import br.com.solutis.venda_service.dto.CarrinhoResponseDto;
import br.com.solutis.venda_service.dto.ProdutoResponseDto;
import br.com.solutis.venda_service.entity.Carrinho;
import br.com.solutis.venda_service.repository.CarrinhoRepository;
import br.com.solutis.venda_service.mapper.CarrinhoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoClient produtoClient;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, ProdutoClient produtoClient) {
        this.carrinhoRepository = carrinhoRepository;
        this.produtoClient = produtoClient;
    }

    public void adicionarItemAoCarrinho(CarrinhoRequestDto dto) {
        ProdutoResponseDto produto = produtoClient.buscarProdutoPorId(dto.produtoId());

        Carrinho carrinho = CarrinhoMapper.toEntity(dto, produto.preco());

        carrinho.setNomeProduto(produto.nome());

        carrinhoRepository.save(carrinho);
    }

    public List<CarrinhoResponseDto> listarCarrinho(Long idConta) {
        List<Carrinho> carrinho = carrinhoRepository.findByIdConta(idConta);

        return carrinho.stream()
                .map(item -> {
                    ProdutoResponseDto produto = produtoClient.buscarProdutoPorId(item.getProdutoId());
                    return new CarrinhoResponseDto(
                            item.getIdCarrinho(),
                            item.getProdutoId(),
                            produto.nome(),
                            item.getQuantidade(),
                            item.getPrecoUnitario(),
                            (item.getPrecoUnitario() * item.getQuantidade())
                    );
                })
                .collect(Collectors.toList());
    }
}
