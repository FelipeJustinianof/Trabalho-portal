package br.com.felipe.portal.portal.noticia.dto;

import br.com.felipe.portal.portal.noticia.models.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PessoaDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String email;

    // Construtor a partir de entidade
    public PessoaDto(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.email = pessoa.getEmail();
    }

    // Conversão de entidade para DTO
    public static PessoaDto fromEntity(Pessoa pessoa) {
        return PessoaDto.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .email(pessoa.getEmail())
                .build();
    }

    // Conversão de DTO para entidade
    public Pessoa toEntity() {
        return Pessoa.builder()
                .id(this.id)
                .nome(this.nome)
                .email(this.email)
                .build();
    }
}
