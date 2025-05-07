import { Component,OnInit } from '@angular/core';
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";
import { FooterComponent } from "../footer/footer.component";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-perfil',
  imports: [HeaderWashabiComponent, FooterComponent,MatFormFieldModule,MatInputModule,MatIconModule,MatButtonModule,FormsModule, MatToolbarModule, RouterLink ],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent implements OnInit {
  mostrarPassword = false;
  perfil = {
    nombre: "",
    email: "",
    password: "",
    sala: "",
    rol: "",
    fechaCreacion: "",
    ultimoAcceso:"",
    imagenUrl:"/img/imagen_usuario_por_defecto.jpg"
  };
  constructor(private http: HttpClient){};

  ngOnInit():void{
    
    const token = localStorage.getItem('token')??"";
    const emailGuardado=localStorage.getItem('email')??"";
    const headers = new HttpHeaders({
      'Authorization': token, 'Content-Type': 'application/json',
  'Accept': 'application/json'
    });
    this.http.get<any>('http://localhost:8080/api/clientes/consultar/'+encodeURIComponent(emailGuardado), {headers })
    .subscribe({
      next:res => {
        this.perfil.nombre = res.nombre;
          this.perfil.email = res.email;
          this.perfil.password = res.contrasenia;
          this.perfil.sala = res.sala;
          this.perfil.rol = res.rol;
          this.perfil.fechaCreacion = res.fechaCreacion;
          this.perfil.ultimoAcceso = localStorage.getItem('ultimoAcceso')??"";
      },
      error:err => {console.log(err)}
  });
  };
 
}
