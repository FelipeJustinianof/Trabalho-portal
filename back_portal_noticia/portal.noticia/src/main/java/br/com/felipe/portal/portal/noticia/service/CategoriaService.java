package br.com.felipe.portal.portal.noticia.service;

import br.com.felipe.portal.portal.noticia.dto.CategoriaDto;
import br.com.felipe.portal.portal.noticia.models.Categoria;
import br.com.felipe.portal.portal.noticia.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {

    @Autowired
    private final CategoriaRepository repository;

    public List<CategoriaDto> findAll() {
        List<Categoria> list = repository.findAll();
        return list.stream().map(CategoriaDto::new).toList();
    }

    public CategoriaDto findById(Integer id) {
        Categoria categoria = repository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return new CategoriaDto(categoria);
    }

    public CategoriaDto insert(CategoriaDto categoriaDto) {
        Categoria categoria = categoriaDto.toEntity();
        //verifica se a categoria pai veio com id e carrega a entidade completa para associar correto
        if(categoria.getCategoriaPai() != null && categoria.getCategoriaPai().getId() != null) {
            Categoria categoriaPai = repository.findById(categoria.getCategoriaPai().getId()).orElseThrow(()-> new RuntimeException("Categoria pai não encontrada"));
            categoria.setCategoriaPai(categoriaPai);
        } else {
            categoria.setCategoriaPai(null);
        }
        Categoria categoriaSalva = repository.save(categoria);
        return new CategoriaDto(categoriaSalva);
    }

}
