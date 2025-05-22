package br.com.solutis.acabou_o_mony.service;

import br.com.solutis.acabou_o_mony.entity.conta.Conta;
import br.com.solutis.acabou_o_mony.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    public Conta encontrarPorIdCartao(Integer id) {
        if (id == null) return null;

        return (repository.existsByCartao_Id(id)) ? repository.findByCartao_Id(id) : null;
    }

}
