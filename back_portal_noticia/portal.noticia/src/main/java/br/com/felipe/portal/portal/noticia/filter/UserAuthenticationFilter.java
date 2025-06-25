package br.com.felipe.portal.portal.noticia.filter;


import br.com.felipe.portal.portal.noticia.configuration.SecurityConfig;
import br.com.felipe.portal.portal.noticia.dto.UserDetailsDTO;
import br.com.felipe.portal.portal.noticia.models.Usuario;
import br.com.felipe.portal.portal.noticia.repository.UsuarioRepository;
import br.com.felipe.portal.portal.noticia.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);

            if (token != null && jwtTokenService.isTokenValid(token)) {
                String subject = jwtTokenService.getSubjectFromToken(token);
                var optionalUser = userRepository.findByLogin(subject);
                if (optionalUser.isPresent()) {
                    Usuario user = optionalUser.get();
                    UserDetailsDTO userDetails = new UserDetailsDTO(user);

                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    // Verifica se o endpoint requer autenticação antes de processar a requisição
    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String uriOriginal = request.getRequestURI();

        // Remove barra final se houver
        String uriSemBarra = uriOriginal.endsWith("/") ?
                uriOriginal.substring(0, uriOriginal.length() - 1) :
                uriOriginal;

        final String finalUri = uriSemBarra; // agora final

        return Arrays.stream(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                .noneMatch(publicEndpoint -> {
                    if (publicEndpoint.endsWith("/**")) {
                        String prefix = publicEndpoint.substring(0, publicEndpoint.length() - 3);
                        return finalUri.startsWith(prefix);
                    } else {
                        return publicEndpoint.equalsIgnoreCase(finalUri);
                    }
                });
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/uploads/");
    }

}