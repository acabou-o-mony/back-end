package br.com.solutis.venda_service.service;

import br.com.solutis.venda_service.entity.Carrinho;
import br.com.solutis.venda_service.entity.Pedido;
import br.com.solutis.venda_service.entity.Status;
import br.com.solutis.venda_service.exception.PedidoNotFoundException;
import br.com.solutis.venda_service.repository.CarrinhoRepository;
import br.com.solutis.venda_service.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    private CarrinhoRepository carrinhoRepository;
    private PedidoRepository pedidoRepository;
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        carrinhoRepository = mock(CarrinhoRepository.class);
        pedidoRepository = mock(PedidoRepository.class);
        pedidoService = new PedidoService(carrinhoRepository, pedidoRepository);
    }

    @Test
    void deveFinalizarCarrinhoComSucesso() {
        Long idCarrinho = 1L;
        Long idConta = 10L;

        Carrinho item1 = new Carrinho();
        item1.setQuantidade(2);
        item1.setPrecoUnitario(10.0);

        Carrinho item2 = new Carrinho();
        item2.setQuantidade(1);
        item2.setPrecoUnitario(20.0);

        List<Carrinho> itens = List.of(item1, item2);

        when(carrinhoRepository.findByCarrinhoId_IdCarrinho(idCarrinho)).thenReturn(itens);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido pedido = pedidoService.finalizarCarrinho(idCarrinho, idConta);

        assertEquals(40.0, pedido.getTotal());
        assertEquals(Status.PENDENTE, pedido.getStatus());
        assertEquals(idConta, pedido.getIdConta());
        assertEquals(itens, pedido.getItens());
    }

    @Test
    void deveLancarExcecaoAoFinalizarCarrinhoVazio() {
        Long idCarrinho = 1L;
        when(carrinhoRepository.findByCarrinhoId_IdCarrinho(idCarrinho)).thenReturn(Collections.emptyList());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                pedidoService.finalizarCarrinho(idCarrinho, 10L));

        assertEquals("Carrinho está vazio.", ex.getMessage());
    }

    @Test
    void deveListarTodosOsPedidosQuandoContaNaoFornecida() {
        List<Pedido> pedidos = List.of(new Pedido(), new Pedido());
        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<Pedido> resultado = pedidoService.listarPedidos(Optional.empty());

        assertEquals(2, resultado.size());
    }

    @Test
    void deveListarPedidosPorConta() {
        Long idConta = 10L;
        List<Pedido> pedidos = List.of(new Pedido());
        when(pedidoRepository.findByIdConta(idConta)).thenReturn(pedidos);

        List<Pedido> resultado = pedidoService.listarPedidos(Optional.of(idConta));

        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarPedidoPorIdComSucesso() {
        Long idPedido = 1L;
        Pedido pedido = new Pedido();
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));

        Pedido resultado = pedidoService.buscarPorId(idPedido);

        assertNotNull(resultado);
    }

    @Test
    void deveLancarExcecaoAoBuscarPedidoPorIdInexistente() {
        Long idPedido = 999L;
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.empty());

        PedidoNotFoundException ex = assertThrows(PedidoNotFoundException.class, () ->
                pedidoService.buscarPorId(idPedido));

        assertEquals("Pedido não encontrado para o ID: 999", ex.getMessage());
    }

    @Test
    void deveAtualizarStatusDoPedido() {
        Long idPedido = 1L;
        Pedido pedido = new Pedido();
        pedido.setStatus(Status.PENDENTE);

        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido atualizado = pedidoService.atualizarStatus(idPedido, Status.PAGO);

        assertEquals(Status.PAGO, atualizado.getStatus());
    }
}
