package br.com.felipe.portal.portal.noticia.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String descricao;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPublicacao;
    @ManyToOne
    @JoinColumn(name = "categoriaId")
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "autorId")
    private Autor autor;
    private String imagemUrl;
}
