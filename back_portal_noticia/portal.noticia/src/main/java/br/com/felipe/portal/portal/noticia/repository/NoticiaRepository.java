package br.com.felipe.portal.portal.noticia.repository;

import br.com.felipe.portal.portal.noticia.models.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {
    List<Noticia> findByCategoriaId(Integer categoriaId);
}
