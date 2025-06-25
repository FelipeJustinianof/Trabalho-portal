import { Component, OnInit } from '@angular/core';
import { Categoria } from '../../models/categoria';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-template',
  templateUrl: './template.component.html',
  styleUrl: './template.component.scss'
})
export class TemplateComponent implements OnInit {
  apiUrl = "http://localhost:8080/";
  logged = false;
  autor: boolean = false;
  categorias: Categoria[] = [];

  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient

  ) { }

  ngOnInit(): void {
    this.authService.init();
    this.authService.loggedIn$.subscribe(status => {
      this.logged = status;
      this.autor = !!this.authService.getAutor();
    });

    this.http.get<Categoria[]>(`${this.apiUrl}categoria`).subscribe({
      next: (res) => this.categorias = res,
      error: (err) => console.log('Error ao carregar as categorias:', err)
    })
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/home']);
  }

  get categoriasPrincipais(): Categoria[] {
  return this.categorias.filter(c => !c.categoriaPai);
}
}
