package br.com.solutis.usuario_service.controller;

import br.com.solutis.usuario_service.dto.conta.ContaRequestDto;
import br.com.solutis.usuario_service.dto.conta.ContaResponseDto;
import br.com.solutis.usuario_service.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas")
public class ContaController {
    @Autowired
    private ContaService service;

    // CREATE
    @PostMapping
    public ResponseEntity<ContaResponseDto> criar(@RequestBody ContaRequestDto dto){
        return ResponseEntity.ok(service.criar(dto));
    }
}
