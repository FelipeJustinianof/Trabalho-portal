import { Component, OnInit, Output } from '@angular/core';
import { Noticia } from '../../models/noticia.models';
import { Service } from '../../service/service.service';
import { Categoria } from '../../models/categoria';
import { firstValueFrom } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  categorias: Categoria[] = [];
  noticiasPorCategoria: { [categoriaId: number]: Noticia[] } = {};
  carregando: boolean = true;

  constructor(private service: Service, private router: Router) { }

  async ngOnInit() {
    const BACKEND_URL = 'http://localhost:8080';

    try {
      const [categorias, noticias] = await Promise.all([
        firstValueFrom(this.service.listar()),
        firstValueFrom(this.service.getNoticias())
      ]);

      if (categorias) {
        this.categorias = categorias.slice(0, 3);

        const categoriasIds = this.categorias.map(c => c.id);
        this.noticiasPorCategoria = {};

        noticias?.forEach(noticia => {
          // Corrige a URL da imagem
          if (noticia.imagemUrl) {
            noticia.imagemUrl = BACKEND_URL + noticia.imagemUrl;
          }

          // Agrupa por categoria
          if (categoriasIds.includes(noticia.categoriaId)) {
            if (!this.noticiasPorCategoria[noticia.categoriaId]) {
              this.noticiasPorCategoria[noticia.categoriaId] = [];
            }
            this.noticiasPorCategoria[noticia.categoriaId].push(noticia);
          }
        });

        // Ordena notícias por data decrescente em cada categoria
        Object.keys(this.noticiasPorCategoria).forEach(categoriaId => {
          this.noticiasPorCategoria[+categoriaId].sort((a, b) => {
            return new Date(b.dataPublicacao).getTime() - new Date(a.dataPublicacao).getTime();
          });
        });
      }

    } catch (error) {
      console.error('Erro ao carregar categorias e notícias:', error);
    } finally {
      this.carregando = false;
    }
  }

  onNoticia(noticia: Noticia) {
    console.log('Vai navegar para:', noticia.id);
    this.router.navigate(['/noticia', noticia.id])
  }
}