package br.com.felipe.portal.portal.noticia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroDto {
    private String nome;
    private String email;
    private String login;
    private String senha;
    private boolean autor;
}
