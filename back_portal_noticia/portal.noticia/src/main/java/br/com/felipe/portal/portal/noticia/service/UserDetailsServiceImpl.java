package br.com.felipe.portal.portal.noticia.service;


import br.com.felipe.portal.portal.noticia.dto.UserDetailsDTO;
import br.com.felipe.portal.portal.noticia.models.Usuario;
import br.com.felipe.portal.portal.noticia.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> nome = userRepository.findByLogin(username);

        return nome.map(UserDetailsDTO::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}