package br.com.felipe.portal.portal.noticia.dto;

import br.com.felipe.portal.portal.noticia.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto {
    private Integer id;
    private String login;
    private String senha;
    private PessoaDto pessoa;

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.senha = usuario.getSenha();
        this.pessoa = new PessoaDto(usuario.getPessoa());
    }

    // Conversão de entidade para DTO
    public static UsuarioDto fromEntity(Usuario usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .login(usuario.getLogin())
                .senha(usuario.getSenha())
                .pessoa(PessoaDto.fromEntity(usuario.getPessoa()))
                .build();
    }
    // Conversão de DTO para entidade
    public Usuario toEntity() {
        return Usuario.builder()
                .id(this.id)
                .login(this.login)
                .senha(this.senha)
                .pessoa(this.pessoa.toEntity())
                .build();
    }
}
