import { Component } from '@angular/core';
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";
import { FooterComponent } from "../footer/footer.component";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { HttpClient } from '@angular/common/http';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NotificacionComponent } from '../notificacion/notificacion.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [HeaderWashabiComponent, FooterComponent,CommonModule,NotificacionComponent, MatFormFieldModule, MatInputModule, MatButtonModule, MatCardModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  
  mensaje: string="";

  tipo: 'exito' | 'error' = 'exito';
   loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    contrasenia: new FormControl('', Validators.required)
  });
  constructor(private http: HttpClient,private router: Router) {}

   onSubmit(){
    const email = this.loginForm.get('email')?.value ?? ''; 
    const password = this.loginForm.get('contrasenia')?.value ?? ''; 
    this.http.get<{ token: string; mensaje: string }>(
      'http://localhost:8080/api/clientes/login',
      { params:{email,password} }
    ).subscribe({
    next: res => {

      // Guardamos el token en el localStorage
      localStorage.setItem('token', res.token);

      setTimeout(() => {
        setTimeout(() => {
          this.router.navigate(['/perfil']);
        }, 2000);
      }, 0); //Lo hago para se muestre la tarjeta por eso le meto retardo

     this.mensaje="Cargando "+res.mensaje+"...."
     this.tipo="exito"
    },
    error: err => {
      console.log(err)
      this.mensaje="Inválido la contraseña o el correo"
      this.tipo="error"
    
    }
  });
   
  }

}
