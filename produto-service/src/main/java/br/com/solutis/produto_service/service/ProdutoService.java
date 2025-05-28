package br.com.solutis.produto_service.service;

import br.com.solutis.produto_service.entity.Produto;
import br.com.solutis.produto_service.exception.EntidadeConflitoException;
import br.com.solutis.produto_service.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import br.com.solutis.produto_service.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto cadastrar(Produto produto){

        if (repository.existsByNome(produto.getNome())) {
            throw new EntidadeConflitoException("Produto com nome '%s' já cadastrado".formatted(produto.getNome()));
        }

        return repository.save(produto);
    }

    public List<Produto> listar(){
        return repository.findAll();
    }

    public Produto buscarPorId(Long id){
        return repository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Produto de id %d não encontrada".formatted(id)));
    }

    public Produto atualizar(Produto produtoComAtualizacoes) {
        Produto produtoExistente = repository.findById(produtoComAtualizacoes.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Produto de id %d não encontrada".formatted(produtoComAtualizacoes.getId())
                ));

        produtoExistente.setEstoque(produtoComAtualizacoes.getEstoque());
        produtoExistente.setAtivo(produtoComAtualizacoes.getAtivo());

        return repository.save(produtoExistente);
    }

    public void deletar(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new EntidadeNaoEncontradaException("Produto de id %d não encontrada".formatted(id));
        }

    }
}
