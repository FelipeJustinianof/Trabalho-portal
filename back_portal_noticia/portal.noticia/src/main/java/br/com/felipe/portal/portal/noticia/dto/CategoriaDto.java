package br.com.felipe.portal.portal.noticia.dto;

import br.com.felipe.portal.portal.noticia.models.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaDto {


    private Integer id;
    private String descricao;
    private CategoriaDto categoriaPai;
    private List<CategoriaDto> subcategorias;

    // Construtor para conversão direta da entidade
    public CategoriaDto(Categoria categoria) {
        this(categoria, 0);
    }

    private CategoriaDto(Categoria categoria, int nivel) {
        this.id = categoria.getId();
        this.descricao = categoria.getDescricao();

        if (nivel < 1) {  // limite a profundidade da recursão
            if (categoria.getCategoriaPai() != null) {
                this.categoriaPai = new CategoriaDto(categoria.getCategoriaPai(), nivel + 1);
            }

            if (categoria.getSubcategorias() != null) {
                this.subcategorias = categoria.getSubcategorias()
                        .stream()
                        .map(sub -> new CategoriaDto(sub, nivel + 1))
                        .collect(Collectors.toList());
            }
        }
    }

    public static CategoriaDto fromEntity(Categoria categoria) {
        return new CategoriaDto(categoria);
    }

    public Categoria toEntity() {
        Categoria categoria = Categoria.builder()
                .id(this.id)
                .descricao(this.descricao)
                .categoriaPai(this.categoriaPai != null ? this.categoriaPai.toEntity() : null)
                .build();

        if (this.subcategorias != null) {
            categoria.setSubcategorias(
                    this.subcategorias.stream()
                            .map(CategoriaDto::toEntity)
                            .collect(Collectors.toList())
            );
        }
        return categoria;
    }



}
