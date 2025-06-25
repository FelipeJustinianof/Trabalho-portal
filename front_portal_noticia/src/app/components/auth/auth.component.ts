import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Service } from '../../service/service.service';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Login } from '../../models/login.models';

@Component({
  selector: 'app-auth',
  providers: [Service],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']

})
export class AuthComponent {
  login = new FormControl('', [Validators.required, Validators.email]);
  senha = new FormControl('', [Validators.required]);
  hide = true;

  constructor(
    private service: Service,
    private router: Router,
    private toastr: ToastrService
  ) {
  }

  acess() {
    let data: Login = {
      login: this.login.value as string,
      senha: this.senha.value as string
    }

    this.service.login(data).subscribe({
      next: (r) => {
        this.toastr.success("Login feito com sucesso!")
        this.router.navigate(['/home'])
      },
      error: (r) => {
        this.toastr.error("Login ou email invalido!")
      }
    });
  }
}