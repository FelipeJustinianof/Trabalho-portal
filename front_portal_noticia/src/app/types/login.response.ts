export interface LoginResponse {
  token: string;
  nome: string;
  email?: string;
  autor?: { id: number; nome: string };
}