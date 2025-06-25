package br.com.felipe.portal.portal.noticia.controller;

import br.com.felipe.portal.portal.noticia.dto.CategoriaDto;
import br.com.felipe.portal.portal.noticia.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/categoria")
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> findAll() {
        List<CategoriaDto> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> findById(@PathVariable Integer id) {
        CategoriaDto categoriaDto = service.findById(id);
        return ResponseEntity.ok(categoriaDto);
    }

    @PostMapping(value = "/criar")
    public ResponseEntity<CategoriaDto> insert(@RequestBody CategoriaDto dto) {
        CategoriaDto categoriaSalvar = service.insert(dto);
        return ResponseEntity.ok(categoriaSalvar);
    }
}
