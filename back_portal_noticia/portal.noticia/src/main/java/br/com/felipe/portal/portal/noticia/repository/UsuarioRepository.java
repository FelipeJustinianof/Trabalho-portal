package br.com.felipe.portal.portal.noticia.repository;

import br.com.felipe.portal.portal.noticia.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLoginAndSenha(String login, String senha);
    Optional<Usuario> findByLogin(String login);
}
