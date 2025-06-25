import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AbstractControl, AbstractControlOptions, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Service } from '../../service/service.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
})

export class RegisterComponent implements OnInit {

  hide = true;
  form!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private service: Service,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      nome: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      senha: [null, [
        Validators.required,
        Validators.pattern(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/)
      ]],
      repetirSenha: [null, [Validators.required]],
      autor: [false, [Validators.required]]
    }, {
      validators: this.confirmarSenha('senha', 'repetirSenha')
    } as AbstractControlOptions);
  }

  get nome(): FormControl {
    return this.form.get('nome') as FormControl;
  }

  get email(): FormControl {
    return this.form.get('email') as FormControl;
  }

  get senha(): FormControl {
    return this.form.get('senha') as FormControl;
  }

  get repetirSenha(): FormControl {
    return this.form.get('repetirSenha') as FormControl;
  }

  get autor(): FormControl {
    return this.form.get('autor') as FormControl;
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      console.log('Formulário inválido');
      return;
    }

    const { nome, email, senha, autor } = this.form.value;

    const payload = {
      nome,
      email,
      senha,
      autor,
      login: email
    };

    this.service.post(payload, 'cadastrar').subscribe({
      next: (r) => {
        this.toastr.success("Cadastro criado com sucesso!")
        this.router.navigate(['/home'])
      }, 
      error: err => {
        console.log("erro: ", err)
        this.toastr.error("erro ao cadastrar o usuario!")
      }
    });
  }

  confirmarSenha(controlName: string, matchingControlName: string) {
    return (control: AbstractControl): ValidationErrors | null => {
      const group = control as FormGroup;
      const senha = group.controls[controlName];
      const repetirSenha = group.controls[matchingControlName];

      if (!senha || !repetirSenha) return null;

      return senha.value === repetirSenha.value
        ? null
        : { confirmarSenha: true };
    };
  }

}
