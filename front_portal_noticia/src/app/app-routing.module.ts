import {  NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { TemplateComponent } from './components/template/template.component';
import { authGuard } from './guard/auth.guard';
import { NewsComponent } from './components/news/news.component';
import { CategoryComponent } from './components/category/category.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthComponent } from './components/auth/auth.component';
import { UniquePageComponent } from './components/unique-page/unique-page.component';

import { CategoriaNoticiasComponent } from './components/categoria_noticia/categoria.noticia.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: '',
    component: TemplateComponent,
    children: [
      {
        path: 'home',
        component: HomeComponent,
        pathMatch: 'full'
      },
      {
        path: 'noticia/:id',
        component: UniquePageComponent
      },
      {
        path: 'noticia/criar',
        component: NewsComponent,
        canActivate: [authGuard]
      },
      {
        path: 'categoria/criar',
        component: CategoryComponent,
        canActivate: [authGuard]
      },
      {
        path: 'categoria/:id',
        component: CategoriaNoticiasComponent
      }
    ]
  },
  {
    path: 'cadastro',
    component: RegisterComponent
  },
  {
    path: 'login',
    component: AuthComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
