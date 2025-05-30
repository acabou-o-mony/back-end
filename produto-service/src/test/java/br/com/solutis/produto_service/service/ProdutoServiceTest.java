package br.com.solutis.produto_service.service;

import br.com.solutis.produto_service.entity.Produto;
import br.com.solutis.produto_service.exception.EntidadeConflitoException;
import br.com.solutis.produto_service.exception.EntidadeNaoEncontradaException;
import br.com.solutis.produto_service.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    private Produto produto;

    @BeforeEach
    void setUp(){
        produto = new Produto(
                1L,
                "Coca-Cola",
                "Cola-Cola 350ml",
                5.50,
                5,
                true,
                LocalDate.of(2025, 05, 25)
        );
    }

    @Test
    @DisplayName("Deve cadastrar com sucesso um produto ao passar um produto válido")
    void deveCadastrarUmProdutoComSucesso() {

        when(repository.existsByNome(produto.getNome())).thenReturn(false);
        when(repository.save(any(Produto.class))).thenReturn(produto);

        Produto resultado = service.cadastrar(produto);

        assertNotNull(resultado);
        assertEquals(produto.getNome(), resultado.getNome());
        assertEquals(produto.getDescricao(), resultado.getDescricao());
        assertEquals(produto.getPrecoUnitario(), resultado.getPrecoUnitario());
        assertEquals(produto.getEstoque(), resultado.getEstoque());
        assertTrue(resultado.getAtivo());
        assertEquals(produto.getDataCriacao(), resultado.getDataCriacao());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar produto já existente")
    void deveLancarExcecaoSeProdutoJaExiste() {
        when(repository.existsByNome(produto.getNome())).thenReturn(true);

        EntidadeConflitoException excecao = assertThrows(
                EntidadeConflitoException.class,
                () -> service.cadastrar(produto)
        );

        assertEquals("Produto com nome '%s' já cadastrado".formatted(produto.getNome())
                , excecao.getMessage());
    }

    @Test
    @DisplayName("Deve listar todos os produtos ao chamar o metodo de listar")
    void deveListarTodosOsProdutos() {
        when(repository.findAll()).thenReturn(List.of(produto));

        List<Produto> produtos = service.listar();

        assertEquals(1, produtos.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar com sucesso o produto com ID 1")
    void deveBuscarProdutoPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        Produto resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(produto.getNome(), resultado.getNome());
        assertEquals(produto.getDescricao(), resultado.getDescricao());
        assertEquals(produto.getPrecoUnitario(), resultado.getPrecoUnitario());
        assertEquals(produto.getEstoque(), resultado.getEstoque());
        assertTrue(resultado.getAtivo());
        assertEquals(produto.getDataCriacao(), resultado.getDataCriacao());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lancar exceção ao buscar um produto com ID inexistente no banco")
    void deveLancarExcecaoAoBuscarUmProdutoComIdInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscarPorId(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar com sucesso ao buscar produto com ID 1")
    void deveAtualizarProdutoComSucessoAoBuscarPorId() {

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenReturn(produto);

        Produto resultado = service.atualizar(produto);

        produto.setId(1L);
        produto.setEstoque(50);
        produto.setAtivo(false);

        assertEquals(50, resultado.getEstoque());
        assertFalse(resultado.getAtivo());
        verify(repository).save(produto);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com ID inexistente")
    void deveLancarExcecaoAAtualizarProdutoComIdInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.atualizar(produto));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve deletar com sucesso o produto com ID 1")
    void deveDeletarProdutoComSucessoAoBuscarPorId() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar ID inexistente no banco")
    void deveLancarExcecaoAoDeletarIdInexistenteNoBanco() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.deletar(1L));
        verify(repository, never()).deleteById(1L);
    }

    @Test
    @DisplayName("Deve listar todos os produtos ativos ao chamar o metodo de listar")
    void deveListarTodosOsProdutosAtivos() {
        when(repository.findByAtivoTrue()).thenReturn(List.of(produto));

        List<Produto> produtos = service.listarAtivos();

        assertEquals(1, produtos.size());
        verify(repository, times(1)).findByAtivoTrue();
    }

    @Test
    @DisplayName("Deve listar todos os produtos que contenham parte do nome ao chamar o método buscarPorNome")
    void deveListarProdutosPorNome() {
        when(repository.findByNomeContainingIgnoreCase("coca-cola"))
                .thenReturn(List.of(produto));

        List<Produto> produtos = service.buscarPorNome("coca-cola");

        assertEquals(1, produtos.size());
        verify(repository, times(1)).findByNomeContainingIgnoreCase("coca-cola");
    }

}