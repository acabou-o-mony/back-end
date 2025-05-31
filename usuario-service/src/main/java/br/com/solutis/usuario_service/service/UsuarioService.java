package br.com.solutis.usuario_service.service;

import br.com.solutis.usuario_service.config.jwtConfig.GerenciadorTokenJwt;
import br.com.solutis.usuario_service.dto.usuario.UsuarioRequestDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioResponseDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioTokenDto;
import br.com.solutis.usuario_service.dto.usuario.UsuarioUpdateDto;
import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.usuario_service.exceptions.EntidadeNaoEncontradaException;
import br.com.solutis.usuario_service.mapper.UsuarioMapper;
import br.com.solutis.usuario_service.repository.UsuarioRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {
    // IMPORTS DE GERENCIADOR DE TOKEN
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    @Autowired
    private AuthenticationManager authenticationManager;

    private UsuarioMapper mapper = new UsuarioMapper(); // Classe nativa do projeto

    @Autowired
    private UsuarioRespository respository;

    // CREATE
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto){
        Usuario user = mapper.toEntity(dto);

        // Criptografia de senha
        user.setSenha(passwordEncoder.encode(user.getSenha()));

        return mapper.toDto(respository.save(user));
    }

    // READ
    public List<UsuarioResponseDto> listar(){
        return mapper.toListDto(respository.findAll());
    }

    public UsuarioResponseDto listarPorId(Integer id){
        Usuario entity = respository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com id %d não encontrado".formatted(id)));

        return mapper.toDto(entity);
    }

    // UPDATE
    public UsuarioResponseDto atualizar(Integer id, UsuarioUpdateDto request){
        Usuario user = respository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário para atualização com id %d não encontrado".formatted(id)));

        user.setId(id);
        user.setNome(request.getNome());
        user.setSenha(request.getSenha());
        user.setTelefone(request.getTelefone());
        user.setTipo(request.getTipo());
        user.setAtivo(request.getAtivo());

        return mapper.toDto(respository.save(user));
    }

    // DELETE
    public void deletarPorId(Integer id){
        if(!respository.existsById(id)){
            throw new EntidadeNaoEncontradaException("Usuário para deleção com id %d não encontrado".formatted(id));
        }

        respository.deleteById(id);
    }

    // AUTENTICAÇÃO E GERENCIAMENTO DE TOKEN
    public UsuarioTokenDto autenticar(Usuario usuario){
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), usuario.getSenha()
        );
        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                respository.findByEmail(usuario.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário com email %s não encontrado".formatted(usuario.getEmail()))
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return mapper.of(usuarioAutenticado, token);
    }
}
