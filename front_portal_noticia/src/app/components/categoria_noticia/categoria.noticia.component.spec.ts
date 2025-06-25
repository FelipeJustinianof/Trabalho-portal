import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriaNoticiaComponent } from './categoria.noticia.component';

describe('CategoriaNoticiaComponent', () => {
  let component: CategoriaNoticiaComponent;
  let fixture: ComponentFixture<CategoriaNoticiaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CategoriaNoticiaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CategoriaNoticiaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
