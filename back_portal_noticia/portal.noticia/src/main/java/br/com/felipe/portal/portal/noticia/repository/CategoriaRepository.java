package br.com.felipe.portal.portal.noticia.repository;

import br.com.felipe.portal.portal.noticia.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
