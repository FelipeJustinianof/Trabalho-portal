package br.com.felipe.portal.portal.noticia.repository;

import br.com.felipe.portal.portal.noticia.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    Optional<Autor> findByPessoaEmail(String email);
}
