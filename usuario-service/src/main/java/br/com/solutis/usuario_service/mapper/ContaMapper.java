package br.com.solutis.usuario_service.mapper;

import br.com.solutis.usuario_service.dto.cartao.CartaoResponseDto;
import br.com.solutis.usuario_service.dto.conta.ContaRequestDto;
import br.com.solutis.usuario_service.dto.conta.ContaResponseDto;
import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.usuario_service.entity.cartao.Cartao;
import br.com.solutis.usuario_service.entity.conta.Conta;

import java.util.Calendar;
import java.util.Date;

public class ContaMapper {
    public Conta toEntity(ContaRequestDto dto){
        Conta entity = new Conta();
        Cartao cartao = new Cartao();

        // VARIAVEL DE DATA DE CRIACAO DE CONTA E CARTAO
        Date date = new Date();

        // SETTERS DE ENTIDADE COM PAYLOAD
        entity.setNumero(dto.getNumero());
        entity.setAgencia(dto.getAgencia());
        entity.setTipo(dto.getTipo());
        entity.setSaldo(dto.getSaldo());
        entity.setStatus(dto.getStatus());
        entity.setData_criacao(date);
        // CARTÃO A SER GERADO!

        // SETTERS DE CARTÃO COM PAYLOAD
        cartao.setNumero(dto.getCartao().getNumero());
        cartao.setCvv(dto.getCartao().getCvv());
        cartao.setTipo(dto.getCartao().getTipo());
        cartao.setBanco(dto.getCartao().getBanco());

        // PARA GERACAO DE VALIDADE DO CARTAO
        Calendar cal = Calendar.getInstance();
        cal.setTime(date); // Data base
        cal.add(Calendar.YEAR, 5); // 5 anos adiconados
        // SETTERS DE CARTAO NA CRIACAO
        cartao.setValidade(cal.getTime());
        cartao.setLimite(500.0);

        // MESCLANDO ENTIDADES
        entity.setCartao(cartao);

        return entity;
    }

    public ContaResponseDto toDto(Conta conta){
        ContaResponseDto dto = new ContaResponseDto();
        CartaoResponseDto ctDto = new CartaoResponseDto();

        // Setters de conta DTO
        dto.setId(conta.getId());
        dto.setNumero(conta.getNumero());
        dto.setAgencia(conta.getAgencia());
        dto.setSaldo(conta.getSaldo());
        dto.setData_criacao(conta.getData_criacao());

        // Setters de cartao DTO
        ctDto.setValidade(conta.getCartao().getValidade());
        ctDto.setTipo(conta.getCartao().getTipo());
        ctDto.setLimite(conta.getCartao().getLimite());

        dto.setCartao(ctDto);

        return dto;
    }
}
