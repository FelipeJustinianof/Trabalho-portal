import { Component, inject, OnInit } from '@angular/core';
import { Noticia } from '../../models/noticia.models';
import { ActivatedRoute } from '@angular/router';
import { Service } from '../../service/service.service';

@Component({
  selector: 'app-unique-page',
  templateUrl: './unique-page.component.html',
  styleUrl: './unique-page.component.scss'
})
export class UniquePageComponent implements OnInit {
  noticia!: Noticia;
  carregando = true;

  constructor(private route: ActivatedRoute, private service: Service) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.service.getById<Noticia>('noticia', id).subscribe({
        next: (noticia) => {
          // Ajusta a URL da imagem para o backend
          if (noticia.imagemUrl) {
            noticia.imagemUrl = `http://localhost:8080${noticia.imagemUrl}`;
          }
          this.noticia = noticia;
          this.carregando = false;
        },
        error: (err) => {
          console.error('Erro ao carregar notícia:', err);
          this.carregando = false;
        }
      });
    }
  }
}