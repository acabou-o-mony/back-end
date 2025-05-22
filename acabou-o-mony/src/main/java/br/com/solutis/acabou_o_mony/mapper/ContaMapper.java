package br.com.solutis.acabou_o_mony.mapper;

import br.com.solutis.acabou_o_mony.dto.conta.ContaRequestDto;
import br.com.solutis.acabou_o_mony.dto.conta.ContaResponseDto;
import br.com.solutis.acabou_o_mony.entity.conta.Conta;
import br.com.solutis.acabou_o_mony.entity.conta.Status;
import br.com.solutis.acabou_o_mony.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Date;

public class ContaMapper {

    @Autowired
    private UsuarioRepository usuarioRepo;

    public Conta toEntity(ContaRequestDto req) {
        return (req == null) ? null : new Conta(null, req.getNumero(), req.getAgencia(), req.getTipo(), req.getSaldo(), Status.ATIVO, Date.from(Instant.now()), null, usuarioRepo.findById(req.getIdUsuario()).get());
    }

    public ContaResponseDto toResponse(Conta entity) {
        return (entity == null) ? null : new ContaResponseDto(entity.getNumero(), entity.getAgencia(), entity.getTipo(), entity.getSaldo(), entity.getStatus());
    }

}
