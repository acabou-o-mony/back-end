package br.com.solutis.venda_service.controller;

import br.com.solutis.venda_service.dto.CarrinhoRequestDto;
import br.com.solutis.venda_service.dto.CarrinhoResponseDto;
import br.com.solutis.venda_service.exception.ProductNotFoundException;
import br.com.solutis.venda_service.service.CarrinhoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping
    public ResponseEntity<Void> adicionarAoCarrinho(@RequestBody CarrinhoRequestDto dto) {
        try {
            carrinhoService.adicionarItemAoCarrinho(dto);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{idConta}")
    public ResponseEntity<List<CarrinhoResponseDto>> listarCarrinho(@PathVariable Long idConta) {
        List<CarrinhoResponseDto> carrinho = carrinhoService.listarCarrinho(idConta);

        if (carrinho.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(carrinho);
    }

    @PutMapping("/{idCarrinho}/produtos/{idProduto}")
    public ResponseEntity<Void> atualizarQuantidade(
            @PathVariable Long idCarrinho,
            @PathVariable Long idProduto,
            @RequestBody CarrinhoRequestDto dto) {
        try {
            carrinhoService.atualizarQuantidade(idCarrinho, idProduto, dto.quantidade());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idCarrinho}/produtos/{idProduto}")
    public ResponseEntity<Void> deletarProduto(
            @PathVariable Long idCarrinho,
            @PathVariable Long idProduto) {
        try {
            carrinhoService.deletarProduto(idCarrinho, idProduto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Status 204
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
