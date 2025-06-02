package br.com.solutis.venda_service.service;

import br.com.solutis.venda_service.entity.Carrinho;
import br.com.solutis.venda_service.entity.Pedido;
import br.com.solutis.venda_service.entity.Status;
import br.com.solutis.venda_service.exception.PedidoNotFoundException;
import br.com.solutis.venda_service.repository.CarrinhoRepository;
import br.com.solutis.venda_service.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final CarrinhoRepository carrinhoRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoService(CarrinhoRepository carrinhoRepository, PedidoRepository pedidoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido finalizarCarrinho(Long idCarrinho, Long idConta) {
        List<Carrinho> itens = carrinhoRepository.findByCarrinhoId_IdCarrinho(idCarrinho);

        if (itens.isEmpty()) {
            throw new IllegalArgumentException("Carrinho está vazio.");
        }

        double total = itens.stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();

        Pedido pedido = new Pedido();
        pedido.setIdConta(idConta);
        pedido.setDataCriacao(new Date());
        pedido.setStatus(Status.PENDENTE);
        pedido.setTotal(total);
        pedido.setItens(itens);

        itens.forEach(item -> item.setPedido(pedido));

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return pedidoSalvo;
    }

    public List<Pedido> listarPedidos(Optional<Long> contaId) {
        if (contaId.isPresent()) {
            return pedidoRepository.findByIdConta(contaId.get());
        } else {
            return pedidoRepository.findAll();
        }
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido não encontrado para o ID: " + id));
    }

    public Pedido atualizarStatus(Long id, Status novoStatus) {
        Pedido pedido = buscarPorId(id);
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }
}
