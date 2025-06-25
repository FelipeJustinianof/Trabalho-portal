package br.com.felipe.portal.portal.noticia.controller;


import br.com.felipe.portal.portal.noticia.dto.RecoveryJwtDTO;
import br.com.felipe.portal.portal.noticia.dto.UsuarioDto;
import br.com.felipe.portal.portal.noticia.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    @Autowired
    private final AuthService service;

    @PostMapping
    public ResponseEntity<RecoveryJwtDTO> login(@RequestBody UsuarioDto loginDTO) {
        RecoveryJwtDTO token = service.authenticateUser(loginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
