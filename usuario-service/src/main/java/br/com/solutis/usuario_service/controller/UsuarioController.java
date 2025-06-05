package br.com.solutis.usuario_service.controller;

import br.com.solutis.usuario_service.dto.usuario.*;
import br.com.solutis.usuario_service.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    // CREATE
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDto> cadastrar(@Valid @RequestBody UsuarioRequestDto dto){
        return ResponseEntity.status(201).body(service.cadastrar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto dto){
        return ResponseEntity.ok(service.autenticar(dto));
    }

    // READ
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> listarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.listarPorId(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioUpdateDto dto
    ){
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        service.deletarPorId(id);
        return ResponseEntity.status(204).build();
    }
}
