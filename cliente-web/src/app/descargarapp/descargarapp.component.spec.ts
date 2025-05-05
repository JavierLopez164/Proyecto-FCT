import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DescargarappComponent } from './descargarapp.component';

describe('DescargarappComponent', () => {
  let component: DescargarappComponent;
  let fixture: ComponentFixture<DescargarappComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DescargarappComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DescargarappComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
