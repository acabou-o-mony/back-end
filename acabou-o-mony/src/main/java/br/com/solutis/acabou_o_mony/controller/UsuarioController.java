package br.com.solutis.acabou_o_mony.controller;

import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioRequestDto;
import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioResponseDto;
import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioResponseUpdateDto;
import br.com.solutis.acabou_o_mony.service.UsuarioService;
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
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrar(@Valid @RequestBody UsuarioRequestDto dto){
        return ResponseEntity.status(201).body(service.cadastrar(dto));
    }

    // READ
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar(){
        List<UsuarioResponseDto> dtos = service.listar();

        if(dtos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> listarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.listarPorId(id));
    }

    // UPDATE
    @PostMapping("/{id}")
    public ResponseEntity<UsuarioResponseUpdateDto> atualizar(
            @PathVariable Integer id,
            @RequestBody UsuarioRequestDto dto
    ) {
        UsuarioResponseUpdateDto response = service.atualizar(id, dto);
        return ResponseEntity.status(200).body(response);
    }
}
