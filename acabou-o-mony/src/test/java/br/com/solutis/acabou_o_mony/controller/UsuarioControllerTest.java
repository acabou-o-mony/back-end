package br.com.solutis.acabou_o_mony.controller;

import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioRequestDto;
import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioResponseDto;
import br.com.solutis.acabou_o_mony.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UsuarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioRequestDto usuario;
    private UsuarioResponseDto usuarioResponse;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioRequestDto();
        usuario.setNome("RobertoFingersDeathPunch");
        usuario.setEmail("fivefingersdeathpunch@gmail.com");
        usuario.setSenha("solutis123");
        usuario.setTelefone("(11)942139148");
        usuario.setAtivo(true);

        usuarioResponse = new UsuarioResponseDto();
        usuarioResponse.setId(1);
        usuarioResponse.setNome("RobertoFingersDeathPunch");
        usuarioResponse.setEmail("fivefingersdeathpunch@gmail.com");
    }

    @Test
    @DisplayName("Deve cadastrar um usuário com sucesso e retornar 200")
    void deveCadastrarUsuarioComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    assertTrue(response.contains("\"nome\":\"RobertoFingersDeathPunch\""));
                });

    }

    @Test
    @DisplayName("Deve retornar uma lista de usuarios cadastrados e codigo 200")
    void deveListarUsuarioCadastradosComSucesso() throws Exception {
        usuarioService.cadastrar(usuario);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value(usuarioResponse.getNome()))
                .andExpect(jsonPath("$[0].email").value(usuarioResponse.getEmail()));
    }
}