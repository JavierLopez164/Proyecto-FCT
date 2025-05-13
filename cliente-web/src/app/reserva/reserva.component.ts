import { Component, OnInit } from '@angular/core';
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";
import { FooterComponent } from "../footer/footer.component";
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-reserva',
  imports: [HeaderWashabiComponent, FooterComponent, MatCardModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatButtonModule, MatSnackBarModule, MatDatepickerModule, MatNativeDateModule, MatIconModule, MatAutocompleteModule, MatTooltipModule, ReactiveFormsModule],
  templateUrl: './reserva.component.html',
  styleUrl: './reserva.component.css'
})
export class ReservaComponent implements OnInit {
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  constructor(
    private snackBar: MatSnackBar
  ) { }
  clienteNombre = 'Juan Pérez'; // simulado (normalmente vendría de sesión o token)

  restaurantes = [
    { id: 'r1', nombre: 'Restaurante El Buen Sabor' },
    { id: 'r2', nombre: 'La Parrilla de Don Julio' },
    { id: 'r3', nombre: 'Pizzería Nápoles' }
  ];
  form = new FormGroup({
    fechaHora: new FormControl("", Validators.required),
    nPersonas: new FormControl(1, [Validators.required, Validators.min(1)]),
    estado: new FormControl('PENDIENTE', Validators.required),
    restauranteId: new FormControl(null, [Validators.required, Validators.maxLength(70)])
  });



  onSubmit() {
    if (this.form.valid) {
      this.snackBar.open('Reserva creada con éxito', 'Cerrar', {
        duration: 3000
      });
    }
  }
}
