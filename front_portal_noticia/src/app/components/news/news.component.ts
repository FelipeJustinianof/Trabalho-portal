import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Categoria } from '../../models/categoria';
import { HttpClient } from '@angular/common/http';
import { Service } from '../../service/service.service';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrl: './news.component.scss'
})
export class NewsComponent {
  form: FormGroup;
  categorias: Categoria[] = [];
  subCategoriasFiltradas: Categoria[] = [];
  selectedFile: File | null = null;
  selectedFileName: string | null = null;
  apiUrl = "http://localhost:8080/categoria";

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private service: Service,
    private toastr: ToastrService,
    private authService: AuthService
  ) {
    this.form = this.fb.group({
      titulo: [null, [Validators.required]],
      descricao: [null, [Validators.required]],
      dataPub: [null, [Validators.required, this.dateValidator]],
      categoriaId: [null, [Validators.required]],
      subCategoria: [null]
    });
  }

  ngOnInit() {
    console.log('NoticiaComponent carregado');
    this.listar();
    this.form.get('categoriaId')?.valueChanges.subscribe((idCategoria: number) => {
      this.subCategoriasFiltradas = this.categorias.filter(c => c.categoriaPai?.id === idCategoria);
      this.form.get('subCategoria')?.setValue(null);
    });

  }
  get subCategorias(): Categoria[] {
    return this.categorias.filter(c => c.categoriaPai !== null);
  }


  listar(): void {
    this.http.get<Categoria[]>(this.apiUrl).subscribe({
      next: (res) => this.categorias = res,
      error: (err) => console.log('Erro ao carregar as categorias:', err)
    });
  }

  triggerFileInput() {
    const fileInput = document.getElementById('fileInput') as HTMLInputElement;
    fileInput.click();
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      if (!file.type.startsWith('image/')) {
        this.toastr.error('Por favor, selecione apenas arquivos de imagem.');
        this.selectedFileName = null;
        input.value = '';
        this.selectedFile = null;
        return;
      }
      this.selectedFile = file;
      this.selectedFileName = file.name;
    } else {
      this.selectedFileName = null;
      this.selectedFile = null;
    }
  }

  cadastrar() {
    if (this.form.invalid) {
      this.toastr.error('Preencha todos os campos obrigatórios corretamente.');
      return;
    }

    if (!this.selectedFile) {
      this.toastr.error('Por favor, selecione uma imagem.');
      return;
    }

    const autor = this.authService.getAutor();
    if (!autor) {
      this.toastr.error('Autor não identificado. Faça login novamente.');
      return;
    }

    const { titulo, descricao, dataPub, categoriaId } = this.form.getRawValue();

    // Monta o objeto JSON da notícia
    const noticiaPayload = {
      titulo,
      descricao,
      dataPublicacao: dataPub,
      categoriaId,
      subCategoriaId: this.form.get('subCategoria')?.value || null,
      autorId: autor.id
    };

    const formData = new FormData();

    // Anexa a notícia como JSON serializado
    formData.append(
      'noticia',
      new Blob([JSON.stringify(noticiaPayload)], { type: 'application/json' })
    );

    // Anexa a imagem
    formData.append('imagem', this.selectedFile);

    // Envia a requisição
    this.service.postFormData(formData, `noticia/criar`).subscribe({
      next: () => {
        this.toastr.success('Notícia cadastrada com sucesso!');
        this.form.reset();
        this.selectedFile = null;
        this.selectedFileName = null;
      },
      error: (err) => {
        console.error('Erro no envio:', err);
        this.toastr.error('Erro ao cadastrar notícia. Verifique os campos ou tente novamente.');
      }
    });
  }

  getErrorMessage() {
    return this.form.get('dataPub')?.hasError('data_pub') ? 'Data inválida' : '';
  }

  dateValidator(control: AbstractControl): { [key: string]: string } | null {
    const today = new Date().getTime();

    if (!(control && control.value)) {
      return null;
    }

    return new Date(control.value).getTime() > today
      ? { data_pub: 'Não use datas futuras!' }
      : null;
  }
}
