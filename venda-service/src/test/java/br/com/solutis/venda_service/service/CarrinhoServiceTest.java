package br.com.solutis.venda_service.service;

import br.com.solutis.venda_service.client.ProdutoClient;
import br.com.solutis.venda_service.dto.CarrinhoRequestDto;
import br.com.solutis.venda_service.dto.ProdutoResponseDto;
import br.com.solutis.venda_service.entity.Carrinho;
import br.com.solutis.venda_service.entity.CarrinhoId;
import br.com.solutis.venda_service.exception.ProductNotFoundException;
import br.com.solutis.venda_service.repository.CarrinhoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarrinhoServiceTest {

    private CarrinhoRepository carrinhoRepository;
    private ProdutoClient produtoClient;
    private CarrinhoService carrinhoService;

    @BeforeEach
    void setUp() {
        carrinhoRepository = mock(CarrinhoRepository.class);
        produtoClient = mock(ProdutoClient.class);
        carrinhoService = new CarrinhoService(carrinhoRepository, produtoClient);
    }

    @Test
    void deveAdicionarItemAoCarrinhoComSucesso() {
        CarrinhoRequestDto request = new CarrinhoRequestDto(1L, 2L, 100);
        ProdutoResponseDto produto = new ProdutoResponseDto(2L, "Produto Teste", "aaaa", 10.0, 1, true, LocalDate.of(2021, 10, 10));

        when(produtoClient.buscarProdutoPorId(2L)).thenReturn(produto);

        carrinhoService.adicionarItemAoCarrinho(request);

        verify(carrinhoRepository, times(1)).save(any(Carrinho.class));
    }

    @Test
    void deveLancarExcecaoSeProdutoNaoEncontrado() {
        CarrinhoRequestDto request = new CarrinhoRequestDto(99L, 99L, 100);

        when(produtoClient.buscarProdutoPorId(99L)).thenReturn(null);

        ProductNotFoundException ex = assertThrows(ProductNotFoundException.class, () ->
                carrinhoService.adicionarItemAoCarrinho(request));

        assertEquals("Produto não encontrado para o ID: 99", ex.getMessage());
    }

    @Test
    void deveListarItensDoCarrinho() {
        Long carrinhoId = 100L;

        Carrinho carrinho = new Carrinho();
        CarrinhoId carrinhoIdObj = new CarrinhoId();
        carrinhoIdObj.setIdCarrinho(carrinhoId);
        carrinhoIdObj.setIdProduto(1L);
        carrinho.setCarrinhoId(carrinhoIdObj);
        carrinho.setQuantidade(2);
        carrinho.setPrecoUnitario(15.0);

        ProdutoResponseDto produto = new ProdutoResponseDto(1L, "Produto X", "aaa", 15.0, 10, true, LocalDate.of(2020, 10, 10));

        when(carrinhoRepository.findByCarrinhoId_IdCarrinho(carrinhoId)).thenReturn(List.of(carrinho));
        when(produtoClient.buscarProdutoPorId(1L)).thenReturn(produto);

        var resultado = carrinhoService.listarCarrinho(carrinhoId);

        assertEquals(1, resultado.size());
        assertEquals("Produto X", resultado.get(0).nomeProduto());
        assertEquals(30.0, resultado.get(0).subtotal());
    }

    @Test
    void deveAtualizarQuantidadeComSucesso() {
        Long carrinhoId = 100L;
        Long produtoId = 1L;

        Carrinho carrinho = new Carrinho();
        CarrinhoId carrinhoIdObj = new CarrinhoId();
        carrinhoIdObj.setIdCarrinho(carrinhoId);
        carrinhoIdObj.setIdProduto(produtoId);
        carrinho.setCarrinhoId(carrinhoIdObj);
        carrinho.setQuantidade(2);
        carrinho.setPrecoUnitario(10.0);

        when(carrinhoRepository.findByCarrinhoId_IdCarrinhoAndCarrinhoId_IdProduto(carrinhoId, produtoId))
                .thenReturn(List.of(carrinho));

        carrinhoService.atualizarQuantidade(carrinhoId, produtoId, 5);

        assertEquals(5, carrinho.getQuantidade());
        verify(carrinhoRepository).save(carrinho);
    }

    @Test
    void deveLancarExcecaoAoAtualizarQuantidadeParaProdutoInexistente() {
        Long carrinhoId = 100L;
        Long produtoId = 1L;

        when(carrinhoRepository.findByCarrinhoId_IdCarrinhoAndCarrinhoId_IdProduto(carrinhoId, produtoId))
                .thenReturn(List.of());

        assertThrows(ProductNotFoundException.class, () ->
                carrinhoService.atualizarQuantidade(carrinhoId, produtoId, 3));
    }

    @Test
    void deveDeletarProdutoDoCarrinho() {
        Long carrinhoId = 100L;
        Long produtoId = 1L;

        Carrinho carrinho = new Carrinho();
        CarrinhoId carrinhoIdObj = new CarrinhoId();
        carrinhoIdObj.setIdCarrinho(carrinhoId);
        carrinhoIdObj.setIdProduto(produtoId);
        carrinho.setCarrinhoId(carrinhoIdObj);
        carrinho.setQuantidade(2);
        carrinho.setPrecoUnitario(10.0);

        when(carrinhoRepository.findByCarrinhoId_IdCarrinhoAndCarrinhoId_IdProduto(carrinhoId, produtoId))
                .thenReturn(List.of(carrinho));

        carrinhoService.deletarProduto(carrinhoId, produtoId);

        verify(carrinhoRepository).delete(carrinho);
    }

    @Test
    void deveLancarExcecaoAoDeletarProdutoInexistente() {
        Long carrinhoId = 100L;
        Long produtoId = 1L;

        when(carrinhoRepository.findByCarrinhoId_IdCarrinhoAndCarrinhoId_IdProduto(carrinhoId, produtoId))
                .thenReturn(List.of());

        assertThrows(ProductNotFoundException.class, () ->
                carrinhoService.deletarProduto(carrinhoId, produtoId));
    }
}
