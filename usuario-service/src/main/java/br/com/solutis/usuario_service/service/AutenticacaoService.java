package br.com.solutis.usuario_service.service;

import br.com.solutis.usuario_service.dto.usuario.UsuarioDetalhesDto;
import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.usuario_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRepository respository;

    // Método da interface implementado
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = respository.findByEmail(username);

        if (usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException("Usuário: %s não encontrado!".formatted(username));
        }

        return new UsuarioDetalhesDto(usuarioOpt.get());
    }
}
