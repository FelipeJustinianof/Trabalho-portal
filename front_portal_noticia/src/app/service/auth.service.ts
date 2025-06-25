import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private loggedIn: BehaviorSubject<boolean>;
  public loggedIn$: Observable<boolean>;

  constructor() {
    const hasToken = typeof window !== 'undefined' && !!sessionStorage.getItem('auth-token');
    this.loggedIn = new BehaviorSubject<boolean>(hasToken);
    this.loggedIn$ = this.loggedIn.asObservable();
  }

  init(): void {
    const hasToken = typeof window !== 'undefined' && !!sessionStorage.getItem('auth-token');
    this.loggedIn.next(hasToken);
  }

  setSession(token: string, nome: string, email?: string, autor?: { id: number, nome: string }): void {
    sessionStorage.setItem('auth-token', token);
    sessionStorage.setItem('nome', nome);
    if (email) sessionStorage.setItem('email', email);

    if (autor) {
      sessionStorage.setItem('autor-id', autor.id.toString());
      sessionStorage.setItem('autor-nome', autor.nome);
    } else {
      // limpa se nao for autor
      sessionStorage.removeItem('autor-id');
      sessionStorage.removeItem('autor-nome');
    }

    this.loggedIn.next(true);
  }

  getToken(): string | null {
    if (typeof window === 'undefined') return null;
    return sessionStorage.getItem('auth-token');
  }

  getAutor(): { id: number; nome: string } | null {
    if (typeof window === 'undefined') return null;

    const id = sessionStorage.getItem('autor-id');
    const nome = sessionStorage.getItem('autor-nome');
    if (!id || !nome) return null;

    return { id: parseInt(id, 10), nome };
  }

  isLoggedIn(): boolean {
    return this.loggedIn.value;
  }

  logout(): void {
    if (typeof window !== 'undefined') {
      sessionStorage.clear();
    }
    this.loggedIn.next(false);
  }
}