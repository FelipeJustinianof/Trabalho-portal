package br.com.felipe.portal.portal.noticia.service;

import br.com.felipe.portal.portal.noticia.dto.PessoaDto;
import br.com.felipe.portal.portal.noticia.dto.UsuarioCadastroDto;
import br.com.felipe.portal.portal.noticia.dto.UsuarioDto;
import br.com.felipe.portal.portal.noticia.models.Autor;
import br.com.felipe.portal.portal.noticia.models.Pessoa;
import br.com.felipe.portal.portal.noticia.models.Usuario;
import br.com.felipe.portal.portal.noticia.repository.AutorRepository;
import br.com.felipe.portal.portal.noticia.repository.PessoaRepository;
import br.com.felipe.portal.portal.noticia.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PessoaService {

    @Autowired
    private final PessoaRepository repository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final AutorRepository autorRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public void cadastrarUsuario(UsuarioCadastroDto dto) {
        Pessoa pessoa = Pessoa.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .build();

        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        repository.save(pessoa);

        Usuario usuario = Usuario.builder()
                .login(dto.getLogin())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .pessoa(pessoa)
                .build();
        usuarioRepository.save(usuario);

        if (dto.isAutor()) {
            Autor autor = Autor.builder()
                    .pessoa(pessoa)
                    .build();
            autorRepository.save(autor);
        }
    }

    public void login(UsuarioDto dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(dto.getLogin());

        if (usuarioOpt.isEmpty() ||
                !passwordEncoder.matches(dto.getSenha(), usuarioOpt.get().getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário/senha inválidos");
        }
    }
}
