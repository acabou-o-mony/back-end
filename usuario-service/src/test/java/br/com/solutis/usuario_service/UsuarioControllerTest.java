package br.com.solutis.usuario_service;

import br.com.solutis.usuario_service.dto.usuario.UsuarioLoginDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioRequestDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioResponseDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioTokenDto;
import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.usuario_service.repository.ContaRepository;
import br.com.solutis.usuario_service.repository.UsuarioRepository;
import br.com.solutis.usuario_service.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UsuarioService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private ContaRepository repositoryConta;

    // Objetos para realização de testes
    private UsuarioRequestDto requestDto = new UsuarioRequestDto();
    private UsuarioResponseDto responseDto = new UsuarioResponseDto();
    private UsuarioTokenDto tokenDto = new UsuarioTokenDto();
    private UsuarioLoginDto loginDto = new UsuarioLoginDto();

    @BeforeEach
    void setUp(){
        // Limpando dados antes de realizar testes
        repository.deleteAll();

        // Criação do usuário no banco com senha criptografada
        // Para o teste de integração no endpoint login
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("teste@gmail");
        usuario.setSenha(passwordEncoder.encode("12345"));
        usuario.setTelefone("123456789");
        usuario.setTipo("tipo1");
        usuario.setAtivo(true);

        repository.save(usuario);


        // DTO de request de criação
        requestDto.setNome("Teste");
        requestDto.setEmail("teste@gmail");
        requestDto.setSenha("12345");
        requestDto.setTelefone("290374230");
        requestDto.setTipo("tipo1");
        requestDto.setAtivo(true);

        // DTO de resposta a requisições
        responseDto.setId(1);
        responseDto.setNome("Teste");
        responseDto.setEmail("teste@gmail");

        // DTO de reposta de login
        tokenDto.setId(1);
        tokenDto.setNome("Teste");
        tokenDto.setEmail("teste@gmail");
        tokenDto.setToken("fakeToken");

        // DTO de request de login
        loginDto.setEmail("teste@gmail");
        loginDto.setSenha("12345");
    }

    @Test
    @DisplayName("Quando acionado deve criar um usuário com dados válido e retornar o mesmo")
    void criarUsuario_comDadosValidos_retornarUsuario() throws Exception{
        mockMvc.perform(post("/usuarios/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Teste"))
                .andExpect(jsonPath("$.email").value("teste@gmail"));
    }

    @Test
    @DisplayName("Quando acionado deve autenticar usuário autenticado e retornar o mesmo")
    void autenticarUsuario_comDadosValidos_retornarUsuario() throws Exception {
        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste"))
                .andExpect(jsonPath("$.email").value("teste@gmail"))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @WithMockUser
    @DisplayName("Quando acionado deve retornar uma lista de usuários")
    void listarUsuarios_retornarUsuarios() throws Exception{
        repositoryConta.deleteAll();
        repository.deleteAll();

        Usuario user1 = new Usuario();
        user1.setNome("Teste");
        user1.setEmail("teste@gmail");
        user1.setSenha(passwordEncoder.encode("12345"));
        user1.setTelefone("123456789");
        user1.setTipo("tipo1");
        user1.setAtivo(true);
        repository.save(user1);

        Usuario user2 = new Usuario();
        user2.setNome("Teste2");
        user2.setEmail("teste2@gmail");
        user2.setSenha(passwordEncoder.encode("12345"));
        user2.setTelefone("123456789");
        user2.setTipo("tipo2");
        user2.setAtivo(false);
        repository.save(user2);

        mockMvc.perform(get("/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Teste"))
                .andExpect(jsonPath("$[0].email").value("teste@gmail"))
                .andExpect(jsonPath("$[1].nome").value("Teste2"))
                .andExpect(jsonPath("$[1].email").value("teste2@gmail"));
    }

    @Test
    @WithMockUser
    @DisplayName("Quando acionado deve retornar usuário com dados válidos pelo id especificado")
    void listarUsuario_comDadosValidos_retornarPeloIdEspecificado() throws Exception{
        Usuario user = new Usuario();
        user.setNome("Teste");
        user.setEmail("teste@gmail");
        user.setSenha(passwordEncoder.encode("12345"));
        user.setTelefone("123456789");
        user.setTipo("tipo1");
        user.setAtivo(true);
        Usuario userRetornado = repository.save(user);

        mockMvc.perform(get("/usuarios/" + userRetornado.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste"))
                .andExpect(jsonPath("$.email").value("teste@gmail"));
    }

    @Test
    @WithMockUser
    @DisplayName("Quando acionado deve atualizar usuario com dados validos existente e retorna-lo atualizado")
    void atualizarUsuario_comDadosValidos_retornarUsuarioAtualizado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("teste@gmail");
        usuario.setSenha(passwordEncoder.encode("12345"));
        usuario.setTelefone("123456789");
        usuario.setTipo("tipo1");
        usuario.setAtivo(true);

        Usuario dto = repository.save(usuario);

        Optional<Usuario> userOpt = repository.findById(dto.getId());
        Usuario user = userOpt.get();
        user.setNome("Raul");

        mockMvc.perform(put("/usuarios/" + dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Raul"));
    }

    @Test
    @WithMockUser
    @DisplayName("Quando acionado deve deletar usuário pelo id passado e retornar noContent")
    void deletarUsuario_existente_naoDeveRetornar() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("teste@gmail");
        usuario.setSenha(passwordEncoder.encode("12345"));
        usuario.setTelefone("123456789");
        usuario.setTipo("tipo1");
        usuario.setAtivo(true);

        repositoryConta.deleteAll();
        repository.deleteAll();

        Usuario userResponse = repository.save(usuario);

        mockMvc.perform(delete("/usuarios/" + userResponse.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
