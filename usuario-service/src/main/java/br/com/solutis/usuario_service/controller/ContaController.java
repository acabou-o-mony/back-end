package br.com.solutis.usuario_service.controller;

import br.com.solutis.usuario_service.dto.conta.ContaRequestDto;
import br.com.solutis.usuario_service.dto.conta.ContaResponseDto;
import br.com.solutis.usuario_service.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {
    @Autowired
    private ContaService service;

    // CREATE
    @PostMapping
    public ResponseEntity<ContaResponseDto> criar(@Valid @RequestBody ContaRequestDto dto){
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    // READ
    @GetMapping
    public ResponseEntity<List<ContaResponseDto>> listar(){
        List<ContaResponseDto> dtos = service.listar();

        if (dtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDto> listarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(service.listarPorId(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDto> atualizar(
            @PathVariable Integer id,
            @RequestBody ContaRequestDto dto
    ){
        return ResponseEntity.ok(service.atualizar(Long.valueOf(id), dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ContaResponseDto> deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
