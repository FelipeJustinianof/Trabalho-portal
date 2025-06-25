import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable()
export class SimpleService {

    protected http: HttpClient;
    private baseUrl = "http://localhost:8080/";

    protected options: {
        headers: HttpHeaders;
    };

    protected opcoes: {
        headers: HttpHeaders;
        params?: HttpParams | {
            [param: string]: string | string[];
        };
    } = {
            headers: new HttpHeaders()
        };

    constructor(http: HttpClient) {
        this.http = http;
        this.options = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        };
    }

    getById<T>(resource: string, id: string) {
        return this.http.get<T>(`${this.baseUrl}${resource}/${id}`);
    }

    get(url: string, params?: HttpParams) {
        this.opcoes = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
            params: params
        };
        return this.http.get(`${this.baseUrl}${url}`, this.opcoes).pipe(
            map(this.extractData),
            catchError(this.handleError)
        );
    }

    delete(url: string, params?: HttpParams) {
        this.opcoes = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
            params: params
        };
        return this.http.delete(`${this.baseUrl}${url}`, this.opcoes).pipe(
            map(this.extractData),
            catchError(this.handleError)
        );
    }

    put(t: any, urlComplemento?: string, params?: HttpParams) {
        this.opcoes = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
            params: params
        };
        const complemento = urlComplemento ?? '';
        return this.http.put(this.baseUrl + complemento, t, this.opcoes).pipe(
            map(this.extractData),
            catchError(this.handleError)
        );
    }

    patch(t: any, urlComplemento?: string, params?: HttpParams) {
        this.opcoes = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
            params: params
        };
        const complemento = urlComplemento ?? '';
        return this.http.patch(this.baseUrl + complemento, t, this.opcoes).pipe(
            map(this.extractData),
            catchError(this.handleError)
        );
    }

    post(data: any, url: string) {
        const cleanedUrl = `${this.baseUrl}${url}`.replace(/([^:]\/)\/+/g, '$1');
        return this.http.post(cleanedUrl, data, this.options).pipe(
            map(this.extractData),
            catchError(this.handleError)
        );
    }

    protected extractData(res: any) {
        return res || '';
    }

    protected handleError(error: any) {
        return throwError(() => error?.error ?? 'Erro desconhecido');
    }
}
