export interface Noticia {
    id: string;
    titulo: string;
    descricao: string;
    dataPublicacao: string;
    categoriaId: number;
    autorId: number;
    imagemUrl?: string;
}