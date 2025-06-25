package br.com.felipe.portal.portal.noticia.controller;

import br.com.felipe.portal.portal.noticia.dto.UsuarioCadastroDto;
import br.com.felipe.portal.portal.noticia.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/cadastrar")
public class CadastroController {

    private final PessoaService service;

    @Autowired
    public CadastroController(PessoaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioCadastroDto dto) {
        service.cadastrarUsuario(dto);
        return ResponseEntity.ok(Map.of("mensagem", "Usuário cadastrado com sucesso"));
    }
}
