package br.com.solutis.usuario_service;

import br.com.solutis.usuario_service.config.jwtConfig.GerenciadorTokenJwt;
import br.com.solutis.usuario_service.dto.usuario.UsuarioRequestDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioResponseDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioUpdateDto;
import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.usuario_service.mapper.UsuarioMapper;
import br.com.solutis.usuario_service.repository.UsuarioRepository;
import br.com.solutis.usuario_service.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioMapper mapper;
    @Mock
    private UsuarioRepository repository;
    // MOCKS DE GERÊNCIA JWT
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;

    private UsuarioResponseDto userResponseDto = new UsuarioResponseDto();
    private UsuarioRequestDto userRequestDto = new UsuarioRequestDto();
    private Usuario usuario = new Usuario();

    @BeforeEach
    void setUp(){
        usuario.setId(1);
        usuario.setNome("Teste");
        usuario.setEmail("emailTeste");
        usuario.setSenha("12345");
        usuario.setTelefone("983158730");
        usuario.setTipo("tipo1");
        usuario.setAtivo(true);

        userRequestDto.setNome("Teste");
        userRequestDto.setEmail("emailTeste");
        userRequestDto.setSenha("12345");
        userRequestDto.setTelefone("983158730");
        userRequestDto.setTipo("tipo1");
        userRequestDto.setAtivo(true);

        userResponseDto.setId(1);
        userResponseDto.setNome("Teste");
        userResponseDto.setEmail("emailTeste");
    }

    @Test
    @DisplayName("Deve criar um usuário com os dados válidos e retornar o usuário salva")
    void criarUsuario_comDadosValido_deveRetornarUsuario(){
        // AMBIENTE
        when(mapper.toEntity(userRequestDto)).thenReturn(usuario);
        when(passwordEncoder.encode(userRequestDto.getSenha())).thenReturn("senha_criptografada");
        when(repository.save(usuario)).thenReturn(usuario);
        when(mapper.toDto(usuario)).thenReturn(userResponseDto);

        // AÇÃO
        UsuarioResponseDto response = service.cadastrar(userRequestDto);

        // RETORNO / ASSERÇÃO
        assertNotNull(response);
        assertEquals(response.getNome(), usuario.getNome());
        verify(mapper, times(1)).toEntity(userRequestDto);
        verify(passwordEncoder, times(1)).encode(userRequestDto.getSenha());
        verify(mapper, times(1)).toDto(usuario);
    }

    @Test
    @DisplayName("Deve listar usuários com dados válidos e retornar uma lista de usuários")
    void listarUsuarios_comDadosValidos_retornarListaDeUsuarios(){
        // AMBIENTE
        List<Usuario> usuarios = List.of(
                usuario
        );
        List<UsuarioResponseDto> userDtos = List.of(
                userResponseDto
        );

        when(repository.findAll()).thenReturn(usuarios);
        when(mapper.toListDto(usuarios)).thenReturn(userDtos);

        // AÇÃO
        List<UsuarioResponseDto> dtos = service.listar();

        // RETORNO/ ASSERÇÃO
        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toListDto(usuarios);
    }

    @Test
    @DisplayName("Deve buscar usuário por id e retornar o mesmo pelo id especificado")
    void deveBuscarUsuarioPorId_comDadosValidos_DeveRetonarUsuario(){
        // AMBIENTE
        when(repository.findById(1)).thenReturn(Optional.of(usuario));
        when(mapper.toDto(usuario)).thenReturn(userResponseDto);

        // AÇÃO
        UsuarioResponseDto response = service.listarPorId(1);

        // RETORNO / ASSERÇÃO
        assertNotNull(response);
        assertEquals(usuario.getNome(), response.getNome());
        verify(repository, times(1)).findById(1);
        verify(mapper, times(1)).toDto(usuario);
    }

    @Test
    @DisplayName("Deve atualizar usuario com dados novos válidos e retorna-la atualizada")
    void atualizarUsuario_comDadosValidos_deveRetornarUsuarioAtualizado(){
        // AMBIENTE
        UsuarioUpdateDto userAt = new UsuarioUpdateDto();
        userAt.setNome("Teste Atualização");
        userAt.setSenha("12345");

        when(repository.findById(1)).thenReturn(Optional.of(usuario));
        when(repository.save(usuario)).thenReturn(usuario);
        when(mapper.toDto(usuario)).thenReturn(userResponseDto);

        // AÇÃO
        UsuarioResponseDto response = service.atualizar(1, userAt);

        // RETORNO / ASSERÇÃO
        assertNotNull(response);
    }
}
