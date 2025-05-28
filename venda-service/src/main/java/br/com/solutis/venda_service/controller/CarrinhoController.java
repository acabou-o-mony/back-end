package br.com.solutis.venda_service.controller;

import br.com.solutis.venda_service.dto.CarrinhoRequestDto;
import br.com.solutis.venda_service.dto.CarrinhoResponseDto;
import br.com.solutis.venda_service.service.CarrinhoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Void> adicionarAoCarrinho(@RequestBody CarrinhoRequestDto dto) {
        try {
            carrinhoService.adicionarItemAoCarrinho(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
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
}
