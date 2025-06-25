package br.com.felipe.portal.portal.noticia.service;

import br.com.felipe.portal.portal.noticia.dto.AutorDto;
import br.com.felipe.portal.portal.noticia.dto.RecoveryJwtDTO;
import br.com.felipe.portal.portal.noticia.dto.UserDetailsDTO;
import br.com.felipe.portal.portal.noticia.dto.UsuarioDto;
import br.com.felipe.portal.portal.noticia.models.Autor;
import br.com.felipe.portal.portal.noticia.repository.AutorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AutorRepository repository;

    @Autowired
    private JwtTokenService jwtTokenService;


    public RecoveryJwtDTO authenticateUser(UsuarioDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.getLogin(), loginUserDto.getSenha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsDTO userDetails = (UserDetailsDTO) authentication.getPrincipal();

        String token = jwtTokenService.generateToken(userDetails);

        AutorDto autorDTO = null;
        Optional<Autor> autorOpt = repository.findByPessoaEmail(userDetails.getUsername());
        if (autorOpt.isPresent()) {
            Autor autor = autorOpt.get();
            autorDTO = AutorDto.fromEntity(autor);
        }

        return new RecoveryJwtDTO(token, autorDTO);
    }
}
