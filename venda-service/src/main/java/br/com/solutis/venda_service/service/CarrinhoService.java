package br.com.solutis.venda_service.service;

import br.com.solutis.venda_service.client.ProdutoClient;
import br.com.solutis.venda_service.dto.CarrinhoRequestDto;
import br.com.solutis.venda_service.dto.CarrinhoResponseDto;
import br.com.solutis.venda_service.dto.ProdutoResponseDto;
import br.com.solutis.venda_service.entity.Carrinho;
import br.com.solutis.venda_service.entity.Pedido;
import br.com.solutis.venda_service.exception.ProductNotFoundException;
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
        if (dto.quantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        ProdutoResponseDto produto = produtoClient.buscarProdutoPorId(dto.produtoId());
        if (produto == null) {
            throw new ProductNotFoundException("Produto não encontrado para o ID: " + dto.produtoId());
        }

        Carrinho carrinho = CarrinhoMapper.toEntity(dto, produto.precoUnitario());

        if (carrinho.getPrecoUnitario() == null) {
            throw new IllegalArgumentException("O preço do produto não foi atribuído corretamente.");
        }

        carrinhoRepository.save(carrinho);
    }

    public List<CarrinhoResponseDto> listarCarrinho(Long idCarrinho) {

        List<Carrinho> carrinho = carrinhoRepository.findByCarrinhoId_IdCarrinho(idCarrinho);

        return carrinho.stream()
                .map(item -> {
                    ProdutoResponseDto produto = produtoClient.buscarProdutoPorId(item.getCarrinhoId().getIdProduto());  // Busca o produto pelo idProduto
                    return new CarrinhoResponseDto(
                            item.getCarrinhoId().getIdCarrinho(),
                            item.getCarrinhoId().getIdProduto(),
                            produto.nome(),
                            item.getQuantidade(),
                            item.getPrecoUnitario(),
                            (item.getPrecoUnitario() * item.getQuantidade())
                    );
                })
                .collect(Collectors.toList());
    }

}
