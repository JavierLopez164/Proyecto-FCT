import { Component } from '@angular/core';
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";
import { FooterComponent } from "../footer/footer.component";
import { FormControl, FormGroup,Validators,ReactiveFormsModule  } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
@Component({
  selector: 'app-registrar',
  imports: [HeaderWashabiComponent, FooterComponent, MatFormFieldModule,MatInputModule,MatSnackBarModule,MatButtonModule,MatCardModule,ReactiveFormsModule],
  templateUrl: './registrar.component.html',
  styleUrl: './registrar.component.css'
})
export class RegistrarComponent {
  registroForm = new FormGroup({
    nombre: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    contrasenia: new FormControl('', Validators.required)
  });

  //Inyecto de forma manual la dependencia para establecer conexion con el backend,es decir,construyo la instancia del objeto
  constructor(private http: HttpClient,private router: Router, private snackBar: MatSnackBar) {}

  onSubmit() {
    this.http.post('http://localhost:8080/api/clientes/register', this.registroForm.value,{ 
      headers: { 'Content-Type': 'application/json' },
      responseType: 'text'})
    .subscribe({
      next: res =>{
      this.router.navigate(['/login']);
      },
      
      error: err => {
      this.snackBar.open('Registro mal hecho o con cuenta ya existente', 'Cerrar', {
        duration: 3000
      });
        console.error('Error al registrar:', err)
      }
    });
 this.snackBar.open('Registro hecho con éxito', 'Cerrar', {
        duration: 3000
      });

  }
}
