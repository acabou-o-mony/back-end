package service;

import entity.Produto;
import exception.EntidadeConflitoException;
import exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto cadastrar(Produto produto){

        Optional<Produto> optionalProduto = repository.findById(produto.getId());

        if (optionalProduto.isPresent()){
            throw new EntidadeConflitoException("Produto de id %d já cadastrado".formatted(produto.getId()));
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

    public Produto atualizar(Produto produto){
        if (repository.existsById(produto.getId())){
            return repository.save(produto);
        } else {
            throw new EntidadeNaoEncontradaException("Produto de id %d não encontrada".formatted(produto.getId()));
        }
    }

    public void deletar(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new EntidadeNaoEncontradaException("Produto de id %d não encontrada".formatted(id));
        }

    }
}
