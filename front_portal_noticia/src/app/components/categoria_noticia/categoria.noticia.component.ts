import { Component, OnInit } from '@angular/core';
import { Noticia } from '../../models/noticia.models';
import { Service } from '../../service/service.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-categoria.noticia',
  templateUrl: './categoria.noticia.component.html',
  styleUrl: './categoria.noticia.component.scss'
})
export class CategoriaNoticiasComponent implements OnInit {
  noticias: Noticia[] = [];
  carregando = true;

  constructor(private route: ActivatedRoute, private service: Service, private router: Router) { }

  ngOnInit(): void {
    const BACKEND_URL = 'http://localhost:8080';

    this.route.paramMap.subscribe(paramMap => {
      const categoriaId = Number(paramMap.get('id'));
      if (categoriaId) {
        this.carregando = true;
        this.service.getNoticiasByCategoria(categoriaId).subscribe({
          next: (noticias) => {
            this.noticias = noticias.map(noticia => {
              if (noticia.imagemUrl) {
                noticia.imagemUrl = encodeURI(`${BACKEND_URL}${noticia.imagemUrl}`.replace(/([^:]\/)\/+/g, '$1'));
              }
              return noticia;
            });
            this.carregando = false;
          },
          error: (error) => {
            console.error('Erro ao carregar notícias:', error);
            this.carregando = false;
          }
        });
      }
    });
  }

  verNoticia(noticia: Noticia) {
    this.router.navigate(['/noticia', noticia.id]);
  }
}