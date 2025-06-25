package br.com.felipe.portal.portal.noticia.controller;

import br.com.felipe.portal.portal.noticia.dto.NoticiaDto;
import br.com.felipe.portal.portal.noticia.models.Noticia;
import br.com.felipe.portal.portal.noticia.service.NoticiaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/noticia")
@AllArgsConstructor
public class NoticiaController {

    @Autowired
    private final NoticiaService service;

    @GetMapping
    public ResponseEntity<List<NoticiaDto>> findAll() {
        List<NoticiaDto> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/criar", consumes = "multipart/form-data")
    public ResponseEntity<?> criarNoticia(
            @RequestPart("noticia") NoticiaDto dto,
            @RequestPart("imagem") MultipartFile imagem) {

        try {
            service.insert(dto, imagem);
            return ResponseEntity.ok(Map.of("message", "Notícia criada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Erro ao criar notícia: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticiaDto> findById(@PathVariable Integer id) {
        NoticiaDto dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<NoticiaDto>> getNoticiasPorCategoria(@PathVariable Integer categoriaId) {
        List<NoticiaDto> noticias = service.findByCategoria(categoriaId);
        return ResponseEntity.ok(noticias);
    }
}
