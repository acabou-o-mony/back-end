package br.com.solutis.produto_service.controller;

import br.com.solutis.produto_service.dto.ProdutoRequestDto;
import br.com.solutis.produto_service.dto.ProdutoResponseDto;
import br.com.solutis.produto_service.dto.ProdutoUpdateDto;
import br.com.solutis.produto_service.entity.Produto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import br.com.solutis.produto_service.mapper.ProdutoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.solutis.produto_service.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> cadastrar(@Valid @RequestBody ProdutoRequestDto produtoDto){
        Produto produto = ProdutoMapper.toEntity(produtoDto);
        Produto produtoSalvo = service.cadastrar(produto);

        ProdutoResponseDto responseDto = ProdutoMapper.toDto(produtoSalvo);
        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listar(){
        List<Produto> produtos = service.listar();

        List<ProdutoResponseDto> dtos = produtos.stream().map(ProdutoMapper::toDto).toList();

        if (dtos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> listarPorId(@PathVariable Long id){
        Produto produto = service.buscarPorId(id);
        ProdutoResponseDto responseDto = ProdutoMapper.toDto(produto);

        return ResponseEntity.status(200).body(responseDto);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ProdutoResponseDto>> listarAtivos() {
        List<Produto> ativos = service.listarAtivos();
        List<ProdutoResponseDto> dtos = ativos.stream().map(ProdutoMapper::toDto).toList();

        if (dtos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(dtos);
    }

    @GetMapping("/nomes/{nome}")
    public ResponseEntity<List<ProdutoResponseDto>> buscarPorNome(@PathVariable String nome) {
        List<Produto> produtos = service.buscarPorNome(nome);
        List<ProdutoResponseDto> dtos = produtos.stream()
                .map(ProdutoMapper::toDto)
                .toList();

        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(dtos);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody
    ProdutoUpdateDto produtoDto){
        Produto produto = ProdutoMapper.toUpdate(produtoDto, id);
        Produto produtoAtualizado = service.atualizar(produto);
        ProdutoResponseDto responseDto = ProdutoMapper.toDto(produtoAtualizado);
        return ResponseEntity.status(200).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
