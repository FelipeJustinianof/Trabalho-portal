package br.com.felipe.portal.portal.noticia.dto;

import br.com.felipe.portal.portal.noticia.models.Autor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutorDto {
    private Integer id;
    private String biografia;
    private PessoaDto pessoa;

    // Construtor a partir da entidade
    public AutorDto(Autor autor) {
        this.id = autor.getId();
        this.biografia = autor.getBiografia();
        this.pessoa = new PessoaDto(autor.getPessoa());
    }

    // Conversão de entidade para DTO
    public static AutorDto fromEntity(Autor autor) {
        return AutorDto.builder()
                .id(autor.getId())
                .biografia(autor.getBiografia())
                .pessoa(PessoaDto.fromEntity(autor.getPessoa()))
                .build();
    }

    // Conversão de DTO para entidade
    public Autor toEntity() {
        return Autor.builder()
                .id(this.id)
                .biografia(this.biografia)
                .pessoa(this.pessoa.toEntity())
                .build();
    }
}
