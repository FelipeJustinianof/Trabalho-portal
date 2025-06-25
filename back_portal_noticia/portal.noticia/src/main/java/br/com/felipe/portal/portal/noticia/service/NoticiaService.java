package br.com.felipe.portal.portal.noticia.service;

import br.com.felipe.portal.portal.noticia.dto.NoticiaDto;
import br.com.felipe.portal.portal.noticia.models.Noticia;
import br.com.felipe.portal.portal.noticia.repository.AutorRepository;
import br.com.felipe.portal.portal.noticia.repository.CategoriaRepository;
import br.com.felipe.portal.portal.noticia.repository.NoticiaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class NoticiaService {

    @Autowired
    private final NoticiaRepository repository;

    @Autowired
    private final CategoriaRepository categoriaRepository;

    @Autowired
    private final AutorRepository autorRepository;

    @Value("${upload.path}")
    private String uploadDir;

    public NoticiaService(NoticiaRepository repository, CategoriaRepository categoriaRepository, AutorRepository autorRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.autorRepository = autorRepository;
    }

    public List<NoticiaDto> findAll() {
       List<Noticia> list = repository.findAll();
       return list.stream().map(NoticiaDto::new).toList();
   }

   public NoticiaDto findById(Integer id) {
        Noticia noticia = repository.findById(id).orElseThrow(() -> new RuntimeException("Noticia não encontrada com o id: " + id));
        return new NoticiaDto(noticia);
   }

    public List<NoticiaDto> findByCategoria(Integer categoriaId) {
        List<Noticia> noticias = repository.findByCategoriaId(categoriaId);
        return noticias.stream().map(NoticiaDto::new).toList();
    }

    public void insert(NoticiaDto dto, MultipartFile imagem) {
        String imagemUrl = null;

        if (imagem != null && !imagem.isEmpty()) {
            try {
                // cria a pasta caso não exista
                Path uploadPath = Paths.get(uploadDir);
                Files.createDirectories(uploadPath);

                String fileName = imagem.getOriginalFilename();

                // cria o caminho completo para salvar o arquivo
                Path filePath = uploadPath.resolve(fileName);

                // salva a imagem
                imagem.transferTo(filePath.toFile());

                // Define o caminho relativo para acessar a imagem via HTTP
                imagemUrl = "/uploads/" + fileName;

            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar imagem", e);
            }
        }

        var categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        var autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        Noticia noticia = Noticia.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .dataPublicacao(dto.getDataPublicacao())
                .categoria(categoria)
                .autor(autor)
                .imagemUrl(imagemUrl)
                .build();

        repository.save(noticia);
    }
}
