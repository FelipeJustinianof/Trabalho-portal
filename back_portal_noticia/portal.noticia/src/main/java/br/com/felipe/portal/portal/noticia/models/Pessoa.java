package br.com.felipe.portal.portal.noticia.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;

    @OneToOne(mappedBy = "pessoa")
    private Usuario usuario;

    @OneToOne(mappedBy = "pessoa")
    private Autor autor;
}
