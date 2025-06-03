package br.com.solutis.usuario_service;

import br.com.solutis.usuario_service.dto.cartao.CartaoResponseDto;
import br.com.solutis.usuario_service.dto.conta.ContaRequestDto;
import br.com.solutis.usuario_service.dto.conta.ContaResponseDto;
import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.usuario_service.entity.cartao.Cartao;
import br.com.solutis.usuario_service.entity.conta.Conta;
import br.com.solutis.usuario_service.entity.conta.Status;
import br.com.solutis.usuario_service.entity.conta.Tipo;
import br.com.solutis.usuario_service.mapper.ContaMapper;
import br.com.solutis.usuario_service.repository.ContaRepository;
import br.com.solutis.usuario_service.repository.UsuarioRepository;
import br.com.solutis.usuario_service.service.ContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static br.com.solutis.usuario_service.entity.cartao.Tipo.CREDITO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {
    @Mock
    private ContaRepository contaRepository;
    @Mock
    private UsuarioRepository userRepository;
    @Mock
    private ContaMapper mapper;

    @InjectMocks
    private ContaService service;

    private ContaRequestDto requestDto = new ContaRequestDto();
    private ContaResponseDto responseDto = new ContaResponseDto();
    private Conta conta = new Conta();
    private Usuario usuario = new Usuario();

    @BeforeEach
    void setUp(){
        Date data = new Date();
        Cartao cartao = new Cartao(1, "123", data, "527",
                CREDITO, "Bradesco", 500.0);
        CartaoResponseDto cartaoResponse = new CartaoResponseDto(data, CREDITO, 500.0);

        // CRIAÇÃO DE REQUEST CONTA
        requestDto.setNumero("534");
        requestDto.setAgencia("02394");
        requestDto.setTipo(Tipo.CORRENTE);
        requestDto.setSaldo(321.9);
        requestDto.setStatus(Status.ATIVO);
        requestDto.setCartao(cartao);
        requestDto.setUsuarioId(1);

        // CRIAÇÃO DE RESPONSE CONTA
        responseDto.setId(1);
        responseDto.setNumero("534");
        responseDto.setAgencia("02394");
        responseDto.setSaldo(321.9);
        responseDto.setData_criacao(data);
        responseDto.setCartao(cartaoResponse);

        // CRIAÇÃO DE USUARIO (ATRIBUTOS MÍNIMOS)
        usuario.setId(1);

        // CRIAÇÃO DE CONTA
        conta.setId(1);
        conta.setNumero("534");
        conta.setAgencia("02394");
        conta.setTipo(Tipo.CORRENTE);
        conta.setSaldo(321.9);
        conta.setStatus(Status.ATIVO);
        conta.setData_criacao(data);
        conta.setCartao(cartao);
        conta.setUsuario(usuario);
    }

    @Test
    @DisplayName("Deve criar uma conta com os dados válidos e retornar a conta salva")
    void criarConta_comDadosValido_deveRetornarConta(){
        // AMBIENTE
        when(userRepository.findById(requestDto.getUsuarioId())).thenReturn(Optional.of(usuario));
        when(mapper.toEntity(requestDto)).thenReturn(conta);
        when(contaRepository.save(conta)).thenReturn(conta);
        when(mapper.toDto(conta)).thenReturn(responseDto);

        // AÇÃO
        ContaResponseDto dto = service.criar(requestDto);

        // RETORNO / ASSERÇÃO
        assertNotNull(dto);
        assertEquals(requestDto.getNumero(), dto.getNumero());
        verify(userRepository, times(1)).findById(1);
        verify(mapper, times(1)).toEntity(requestDto);
        verify(mapper, times(1)).toDto(conta);
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    @DisplayName("Deve listar as contas com dados válidos e retornar uma lista de contas")
    void listarContas_comDadosExistentes_deveRetornarListaDeContas(){
        // AMBIENTE
        List<ContaResponseDto> dtos = List.of(
                responseDto
        );
        List<Conta> contas = List.of(
                conta
        );

        when(contaRepository.findAll()).thenReturn(contas);
        when(mapper.toListDto(contas)).thenReturn(dtos);

        // AÇÃO
        List<ContaResponseDto> responseDtos = service.listar();

        // RETORNO / ASSERÇÃO
        assertNotNull(responseDtos);
        assertEquals(1, dtos.size());
        verify(contaRepository, times(1)).findAll();
        verify(mapper, times(1)).toListDto(contas);
    }

    @Test
    @DisplayName("Deve buscar e retornar conta com dados válidos pelo id especificado")
    void buscarConta_comDadosValidos_retornarConta(){
        // AMBIENTE
        when(contaRepository.findById(1)).thenReturn(Optional.of(conta));
        when(mapper.toDto(conta)).thenReturn(responseDto);

        // AÇÃO
        ContaResponseDto response = service.listarPorId(1);

        // RETORNO / ASSERÇÃO
        assertNotNull(response);
        verify(contaRepository, times(1)).findById(1);
        verify(mapper, times(1)).toDto(conta);
    }

    @Test
    @DisplayName("Deve atualizar a conta com dados novos válidos e retorna-la atualizada")
    void atualizarUsuario_comDadosValidos_deveRetornarUsuarioAtualizado(){
        // AMBIENTE
        ContaResponseDto contaAt = new ContaResponseDto();
        contaAt.setId(1);
        contaAt.setNumero("535");
        contaAt.setAgencia("1023");
        contaAt.setSaldo(321.9);
        contaAt.setData_criacao(new Date());

        when(contaRepository.findById(1)).thenReturn(Optional.of(conta));
        when(contaRepository.save(conta)).thenReturn(conta);
        when(mapper.toDto(conta)).thenReturn(contaAt);

        // AÇÃO
        ContaResponseDto response = service.atualizar(1, requestDto);

        // RETORNO / ASSERÇÃO
        assertNotNull(response);
        assertEquals("535", contaAt.getNumero());
        verify(contaRepository, times(1)).findById(1);
        verify(mapper, times(1)).toDto(conta);
    }

    @Test
    @DisplayName("Deve realizar operação de delete e não retornar")
    void deletarUsuario_comIdValido_naoDeveRetornar(){
        // AMBIENTE
        when(contaRepository.existsById(1)).thenReturn(true);

        // AÇÃO
        service.deletar(1);

        // ASSERÇÃO
        verify(contaRepository, times(1)).existsById(1);
        verify(contaRepository, times(1)).deleteById(1);
    }

}
