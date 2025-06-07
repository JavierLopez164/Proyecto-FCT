import { Component } from '@angular/core';
import { HeaderWashabiComponent } from "../../../shared/header-washabi/header-washabi.component";
import { FooterComponent } from "../../../shared/footer/footer.component";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { HttpClient } from '@angular/common/http';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router , RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
@Component({
  selector: 'app-login',
  imports: [HeaderWashabiComponent, FooterComponent, RouterLink ,CommonModule,MatSnackBarModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatCardModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  
   loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')]),
    contrasenia: new FormControl('', Validators.required)
  });

  constructor(private http: HttpClient,private router: Router,private snackBar: MatSnackBar) {}

   onSubmit(){
    const email = this.loginForm.get('email')?.value ?? ''; 
    const password = this.loginForm.get('contrasenia')?.value ?? ''; 
    this.http.post<{ token: string; mensaje: string }>(
      'http://localhost:8080/api/clientes/login',{},
      {params:{email,password}}
    ).subscribe({
    next: res => {
     
      // Guardamos el token en el localStorage
      localStorage.setItem('token', res.token);
        // Guardamos el email
      localStorage.setItem('email', email);
       this.router.navigate(['/perfil']);
       
    },
    error: err => {
       this.snackBar.open('Login incorrecto', 'Cerrar', {
        duration: 3000
      });
      console.log(err)
     
    }
    
  });
    this.snackBar.open('Te has logueado con éxito', 'Cerrar', {
        duration: 3000
      });

  }

}
