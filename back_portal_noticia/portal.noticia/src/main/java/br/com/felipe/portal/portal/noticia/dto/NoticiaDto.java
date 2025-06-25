package br.com.felipe.portal.portal.noticia.dto;

import br.com.felipe.portal.portal.noticia.models.Autor;
import br.com.felipe.portal.portal.noticia.models.Categoria;
import br.com.felipe.portal.portal.noticia.models.Noticia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticiaDto {

    private Integer id;
    private String titulo;
    private String descricao;
    private LocalDate dataPublicacao;
    private String imagemUrl;

    private Integer categoriaId;
    private Integer autorId;

    public NoticiaDto(Noticia noticia) {
        this.id = noticia.getId();
        this.titulo = noticia.getTitulo();
        this.descricao = noticia.getDescricao();
        this.dataPublicacao = noticia.getDataPublicacao();
        this.imagemUrl = noticia.getImagemUrl();

        if (noticia.getCategoria() != null) {
            this.categoriaId = noticia.getCategoria().getId();
        }

        if (noticia.getAutor() != null) {
            this.autorId = noticia.getAutor().getId();
        }
    }

    public Noticia toEntity(Categoria categoria, Autor autor) {
        return Noticia.builder()
                .id(this.id)
                .titulo(this.titulo)
                .descricao(this.descricao)
                .dataPublicacao(this.dataPublicacao)
                .categoria(categoria)
                .autor(autor)
                .imagemUrl(this.imagemUrl)
                .build();
    }
}
