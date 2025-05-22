package br.com.solutis.acabou_o_mony.service;

import br.com.solutis.acabou_o_mony.dto.transacao.TransacaoResumedResponseDto;
import br.com.solutis.acabou_o_mony.entity.conta.Conta;
import br.com.solutis.acabou_o_mony.mapper.TransacaoMapper;
import br.com.solutis.acabou_o_mony.repository.ContaRepository;
import br.com.solutis.acabou_o_mony.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {
    TransacaoMapper mapper = new TransacaoMapper();

    @Autowired
    private TransacaoRepository transacaoRepo;

    @Autowired
    private ContaRepository contaRepo;

    public List<TransacaoResumedResponseDto> listarPorIdConta(Integer id) {
        if (id == null) return null;

        if (contaRepo.findById(id).isPresent()) {
            Integer idCartao = contaRepo.findById(id).get().getCartao().getId();

            List<TransacaoResumedResponseDto> transacoes = transacaoRepo.findAllByCartao_Id(idCartao).stream().map(mapper::toResumedResponse).toList();

            return (transacoes.isEmpty()) ? null : transacoes;
        } else {
            return null;
        }


    }

}
