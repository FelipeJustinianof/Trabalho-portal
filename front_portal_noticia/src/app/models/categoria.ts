export interface Categoria {
    id: number,
    descricao: string,
    categoriaPai?: Categoria,
    subcategorias?: Categoria[]
} 