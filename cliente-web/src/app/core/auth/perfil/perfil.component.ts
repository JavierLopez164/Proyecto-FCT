import { Component,OnInit } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';
import { PerfilService } from '../../../core/services/perfil.service';
import { FooterComponent } from '../../../shared/footer/footer.component';
import { HeaderWashabiComponent } from '../../../shared/header-washabi/header-washabi.component';
@Component({
  selector: 'app-perfil',
  imports: [HeaderWashabiComponent, FooterComponent,MatFormFieldModule,MatInputModule,MatIconModule,MatButtonModule,FormsModule, MatToolbarModule, RouterLink ],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent implements OnInit {
  mostrarPassword = false;
  constructor(private perfilService:PerfilService){}
  perfil = {
    nombre: "",
    email: "",
    password: "",
    sala: "",
    rol: "",
    fechaCreacion: "",
    imagenUrl:""
  };

  ngOnInit():void{
    this.perfilService.obtenerPerfil().subscribe(res=>{
      this.perfil.nombre = res.nombre;
      this.perfil.email = res.email;
      this.perfil.password = res.contrasenia;
      this.perfil.rol = res.rol;
      this.perfil.fechaCreacion = res.fechaCreacion;
      this.perfil.imagenUrl= res.fotoPerfil?.imagenUrl;
    });
  };
 
}
