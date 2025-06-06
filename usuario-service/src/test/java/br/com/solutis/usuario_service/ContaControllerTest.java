package br.com.solutis.usuario_service;

import br.com.solutis.usuario_service.dto.conta.ContaRequestDto;
import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.usuario_service.entity.cartao.Cartao;
import br.com.solutis.usuario_service.entity.conta.Conta;
import br.com.solutis.usuario_service.entity.conta.Status;
import br.com.solutis.usuario_service.entity.conta.Tipo;
import br.com.solutis.usuario_service.mapper.ContaMapper;
import br.com.solutis.usuario_service.mapper.UsuarioMapper;
import br.com.solutis.usuario_service.repository.ContaRepository;
import br.com.solutis.usuario_service.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ContaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private UsuarioRepository userRepository;


    private UsuarioMapper userMapper = new UsuarioMapper();
    private ContaMapper mapper = new ContaMapper();
    private ContaRequestDto requestDto = new ContaRequestDto();
    private Cartao cartao = new Cartao();
    private Usuario user = new Usuario();

    @BeforeEach
    void setUp(){
        contaRepository.deleteAll();
        userRepository.deleteAll();

        // Adicionando usuário para associa-lo a conta
        user.setNome("Teste");
        user.setEmail("teste@email");
        user.setSenha("12345");
        user.setTelefone("02935723");
        user.setTipo("Tipo1");
        user.setAtivo(true);

        Usuario userCadastrado = userRepository.save(user);

        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date); // Data base
        cal.add(Calendar.YEAR, 5);
        cartao.setNumero("2983452");
        cartao.setValidade(cal.getTime());
        cartao.setCvv("555");
        cartao.setTipo(br.com.solutis.usuario_service.entity.cartao.Tipo.CREDITO);
        cartao.setBanco("Itau");
        cartao.setLimite(5000.0);

        requestDto.setNumero("1231");
        requestDto.setAgencia("02983");
        requestDto.setTipo(Tipo.CORRENTE);
        requestDto.setSaldo(542.1);
        requestDto.setStatus(Status.ATIVO);
        requestDto.setCartao(cartao);
        requestDto.setUsuarioId(userCadastrado.getId());
    }

    @Test
    @WithMockUser
    @DisplayName("Quando acionado deve criar uma conta com dados válidos e retorna-la")
    void cadastrarConta_comDadosValidos_retornarConta() throws Exception{
        mockMvc.perform(post("/contas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero").value("1231"));
    }

    @Test
    @WithMockUser
    @DisplayName("Quando acionado deve salvar lista de contas e retorna-la")
    void listarContas_comDadosValidos_retornarListaDeContas() throws Exception{
        Conta conta1 = mapper.toEntity(requestDto);
        Conta conta2 = mapper.toEntity(requestDto);
        conta2.setNumero("203942");

        contaRepository.save(conta1);
        contaRepository.save(conta2);

        mockMvc.perform(get("/contas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numero").value("1231"))
                .andExpect(jsonPath("$[1].numero").value("203942"));
    }

    @Test
    @WithMockUser
    @DisplayName("Quando acionado deve listar contas com dados validos pelo id especificado")
    void listarContas_comDadosValidos_retornarUsuarioPeloId() throws Exception{
        Conta conta = contaRepository.save(mapper.toEntity(requestDto));

        mockMvc.perform(get("/contas/" + conta.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("1231"));
    }

    @Test
    @WithMockUser
    @DisplayName("Quando acionado deve atualizar conta existente e retorna-la")
    void atualizarConta_comDadosValidos_retornarConta() throws Exception{
        Conta conta = contaRepository.save(mapper.toEntity(requestDto));
        conta.setNumero("4958");

        mockMvc.perform(put("/contas/" + conta.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(conta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("4958"));
    }

    @Test
    @WithMockUser
    @DisplayName("Quando acionado deve deletar conta com dados válido pelo id especificado")
    void deletarConta_peloIdEspecifico_naoDeveRetornar() throws Exception{
        Conta conta = contaRepository.save(mapper.toEntity(requestDto));

        mockMvc.perform(delete("/contas/" + conta.getId()))
                .andExpect(status().isNoContent());
    }
}