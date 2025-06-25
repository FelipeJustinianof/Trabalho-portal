import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Service } from '../../service/service.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Categoria } from '../../models/categoria';
import { HttpClient } from '@angular/common/http';
import { MatSelectChange } from '@angular/material/select';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrl: './category.component.scss'
})
export class CategoryComponent implements OnInit {
  form!: FormGroup;
  categorias: Categoria[] = [];
  subCategoriasFiltradas: Categoria[] = [];
  apiUrl = "http://localhost:8080/categoria";

  constructor(
    private formBuilder: FormBuilder,
    private service: Service,
    private http: HttpClient,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      descricao: [null, Validators.required],
      categoriaPai: [null],
      subCategoria: [null]
    })
    this.listar();

    this.form.get('categoriaPai')?.valueChanges.subscribe((idPai) => {
      this.subCategoriasFiltradas = this.categorias.filter(c => c.categoriaPai?.id === idPai);
      this.form.get('subCategoria')?.setValue(null);
    })
  }

  carregarSubcategorias(event: MatSelectChange): void {
    const idPai = event.value;
    this.subCategoriasFiltradas = this.categorias.filter(c => c.categoriaPai?.id === idPai);
    this.form.get('subCategoria')?.setValue(null);
  }

  listar(): void {
    this.http.get<Categoria[]>(this.apiUrl).subscribe({
      next: (res) => this.categorias = res,
      error: (err) => console.log('Erro ao carregar as categorias:', err)
    });
  }


  get descricao(): FormControl {
    return this.form.get('descricao') as FormControl;
  }

  get categoriasPai(): Categoria[] {
    return this.categorias.filter(c => !c.categoriaPai);
  }

  get subCategorias(): Categoria[] {
    return this.categorias.filter(c => c.categoriaPai !== null);
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      console.log('Formulário inválido');
      return;
    }

    const { descricao, categoriaPai, subCategoria } = this.form.value;

    const payload = {
      descricao,
      categoriaPai: categoriaPai ? { id: categoriaPai } : null,
      subCategoria: subCategoria ? { id: subCategoria } : null,
    };

    this.service.post(payload, 'categoria/criar').subscribe({
      next: (r) => {
        this.toastr.success("Categoria criada com sucesso!")
        this.router.navigate(['/home'])
      },
      error: err => {
        console.log("erro: ", err)
        this.toastr.error("erro ao cadastrar a categoria!")
      }
    });
  }
}
