package br.com.solutis.transacao_service;

import br.com.solutis.transacao_service.dto.TransacaoRequestDto;
import br.com.solutis.transacao_service.dto.TransacaoResponseDto;
import br.com.solutis.transacao_service.entity.Status;
import br.com.solutis.transacao_service.entity.Tipo;
import br.com.solutis.transacao_service.entity.Transacao;
import br.com.solutis.transacao_service.mapper.TransacaoMapper;
import br.com.solutis.transacao_service.repository.TransacaoRepository;
import br.com.solutis.transacao_service.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTests {

    @Mock
    TransacaoMapper mapper;

    @Mock
    private TransacaoRepository repository;

    @Mock
    RabbitTemplate template;

    @InjectMocks
    private TransacaoService service;

    private TransacaoRequestDto req;
    private Transacao entity;

    @BeforeEach
    void setUp() {
        req = new TransacaoRequestDto();
        req.setValor(10.60);
        req.setTipo(Tipo.CREDITO);
        req.setDescricao("Descrição teste");
        req.setContexto("Contexto teste");
        req.setCanal("Canal teste");
        req.setCartaoId(1L);
        req.setPedidoId(1L);

        entity = new Transacao();
        entity.setId(1L);
        entity.setValor(10.60);
        entity.setTipo(Tipo.CREDITO);
        entity.setStatus(Status.PENDENTE);
        entity.setDataHora(LocalDateTime.now());
        entity.setDescricao("Descrição teste");
        entity.setContexto("Contexto teste");
        entity.setCanal("Canal teste");
        entity.setCartaoId(1L);
        entity.setPedidoId(1L);
    }

    @Test
    @DisplayName("Deve criar uma transação com dados válidos e retornar a transação criada")
    void criarTransacao_dadosValidos_retornaTransacao() {
        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(any(Transacao.class))).thenReturn(entity);

        Transacao savedEntity = service.novaTransacao(req);

        assertNotNull(savedEntity);
        assertEquals("Descrição teste", savedEntity.getDescricao());
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toEntity(req);
    }

    @Test
    @DisplayName("Deve buscar transações pendentes a partir do id de um cartão e retornar uma lista")
    void buscarTransacoesPendentesComIdDeCartao_retornarListaDeTransacoes() {
        when(repository.findAllByCartaoIdAndStatusEquals(1L, Status.PENDENTE)).thenReturn(Arrays.asList(entity));

        var entities = service.listarPendentesPorId(1L);

        assertEquals(1, entities.size());
        assertEquals("Descrição teste", entities.get(0).getDescricao());
        verify(repository, times(1)).findAllByCartaoIdAndStatusEquals(1L, Status.PENDENTE);
    }

    @Test
    @DisplayName("Deve buscar uma transação por id e retornar a transação correta")
    void buscarTransacaoPorId_retornarTransacao() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Transacao foundEntity = service.buscarPorId(1L);

        assertEquals("Descrição teste", foundEntity.getDescricao());
        verify(repository, times(1)).findById(1L);
    }

}
