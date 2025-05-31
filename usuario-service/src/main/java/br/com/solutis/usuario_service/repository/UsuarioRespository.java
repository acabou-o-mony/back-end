package br.com.solutis.usuario_service.repository;

import br.com.solutis.usuario_service.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRespository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}
