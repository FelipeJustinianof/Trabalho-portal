import { HttpClient } from "@angular/common/http";
import { SimpleService } from "./simple.service";
import { Injectable } from "@angular/core";
import { Login } from "../models/login.models";
import { AuthService } from "./auth.service";
import { catchError, Observable, tap } from "rxjs";
import { LoginResponse } from "../types/login.response";
import { cadastro } from "../models/cadastro.models";
import { Categoria } from "../models/categoria";
import { Noticia } from "../models/noticia.models";

@Injectable({ providedIn: 'root' })

@Injectable({ providedIn: 'root' })
export class Service extends SimpleService {

    apiUrl = 'http://localhost:8080/';

    constructor(http: HttpClient, private auth: AuthService) {
        super(http);
    }

    register(data: cadastro): Observable<any> {
        return this.http.post(`${this.apiUrl}cadastro`, data);
    }

    login(data: Login): Observable<any> {
        return this.http.post<LoginResponse>(`${this.apiUrl}login`, data).pipe(
            tap((res) => {
                console.log('Resposta do login:', res);
                this.auth.setSession(res.token, res.nome, res.email, res.autor);
            })
        );
    }

    listar(): Observable<Categoria[]> {
        return this.get('categoria');
    }

    getNoticias(): Observable<Noticia[]> {
        return this.http.get<Noticia[]>(`${this.apiUrl}noticia`);
    }

    getNoticiasByCategoria(categoriaId: number): Observable<Noticia[]> {
        return this.http.get<Noticia[]>(`${this.apiUrl}noticia/categoria/${categoriaId}`);
    }

    postFormData(formData: FormData, endpoint: string): Observable<any> {
        const url = `${this.apiUrl}${endpoint}`.replace(/([^:]\/)\/+/g, '$1');

        return this.http.post(url, formData).pipe(
            catchError(this.handleError)
        );
    }
}
