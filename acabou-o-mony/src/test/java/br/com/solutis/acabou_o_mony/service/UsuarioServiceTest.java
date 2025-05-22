package br.com.solutis.acabou_o_mony.service;

import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioRequestDto;
import br.com.solutis.acabou_o_mony.dto.usuario.UsuarioResponseDto;
import br.com.solutis.acabou_o_mony.entity.Usuario;
import br.com.solutis.acabou_o_mony.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario0;
    private Usuario usuario1;
    private Usuario usuario2;

    private UsuarioRequestDto usuario0Request;
    private UsuarioRequestDto usuari1Request;
    private UsuarioRequestDto usuario2Request;

    @BeforeEach
    void setUp() {
        usuario0 = new Usuario(
                0,
                "nome_c26dfc32352_3",
                "email_2b9959f0e2f_b",
                "senha_01c4b5f86cc8",
                "telefone_ea384c3aeb37",
                "tipo_e6c72ce89e4e",
                true
        );

        usuario1 = new Usuario(
                1,
                "nome_c26dfc32352_4",
                "email_2b9959f0e2f_c",
                "senha_01c4b5f86cc8",
                "telefone_ea384c3aeb37",
                "tipo_e6c72ce89e4e",
                true
        );

        usuario2 = new Usuario(
                2,
                "nome_c26dfc32352_5",
                "email_2b9959f0e2f_d",
                "senha_01c4b5f86cc8",
                "telefone_ea384c3aeb37",
                "tipo_e6c72ce89e4e",
                true
        );
    }

    @Test
    void cadastrar() {
    }

    @Test
    @DisplayName("Listagem de usuários quando existem usuários cadastrados e retornar a lista dos usuários")
    void listarUsuariosComUsuariosCadastradosRetornarListaDosUsuariosTest() {
        List<Usuario> usuarios = List.of(usuario0, usuario1, usuario2);

        when(repository.findAll()).thenReturn(usuarios);

        List<UsuarioResponseDto> resultados = service.listar();

        assertEquals(3, resultados.size());
        assertEquals("nome_c26dfc32352_3", resultados.get(0).getNome());
        assertEquals("nome_c26dfc32352_4", resultados.get(1).getNome());
        assertEquals("nome_c26dfc32352_5", resultados.get(2).getNome());
    }

    @Test
    @DisplayName("Listagem de usuários quando não existem usuários cadastrados e retornar lista vazia")
    void listarUsuariosSemUsuariosRetornarListaVaziaTest() {
        List<Usuario> usuarios = new ArrayList<>();

        when(repository.findAll()).thenReturn(usuarios);

        List<UsuarioResponseDto> resultados = service.listar();

        assertEquals(0, resultados.size());
    }

    @Test
    void listarUsuariosComErroDeBancoTest() {
        when(repository.findAll()).thenThrow(new DataAccessException("Erro ao acessar o banco") {});

        assertThrows(DataAccessException.class, () -> service.listar());
    }

}