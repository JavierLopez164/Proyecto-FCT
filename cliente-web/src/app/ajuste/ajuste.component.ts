import { Component,OnInit, signal} from '@angular/core';
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";
import { FooterComponent } from "../footer/footer.component";
import { ReactiveFormsModule,FormControl, FormGroup,Validators, } from '@angular/forms';
import { MatIcon } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatExpansionPanelTitle } from '@angular/material/expansion';
import { MatToolbar } from '@angular/material/toolbar';
import { MatExpansionPanel } from '@angular/material/expansion';
import { MatExpansionPanelHeader } from '@angular/material/expansion';
import { RouterLink } from '@angular/router';
import { PerfilService } from '../services/perfil.service';
import { MatExpansionModule} from '@angular/material/expansion';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-ajuste',
  imports: [HeaderWashabiComponent, FooterComponent,RouterLink,MatIconModule,MatExpansionModule,MatIcon,MatExpansionPanelTitle ,MatToolbar,MatExpansionPanel, MatExpansionPanelHeader ,ReactiveFormsModule,MatFormFieldModule,MatInputModule,MatButtonModule,],
  templateUrl: './ajuste.component.html',
  styleUrl: './ajuste.component.css'
})
export class AjusteComponent implements OnInit {
 
  perfil = {
    nombre: "",
    email: "",
    password: "",
    sala: "",
    rol: "",
    imagenUrl:""
  };


  readonly panelOpenState = signal(false);
  constructor( private perfilServicio: PerfilService) {}
  ngOnInit(): void {
    this.perfilServicio.obtenerPerfil().subscribe(
      res=>{
        this.perfil.rol=res.rol;
        this.perfil.nombre=res.nombre;
        this.perfil.email=res.email;
        this.panelOpenState.set(res.rol == 'ROLE_USER');
      }
    )
  }
 
  perfilForm = new FormGroup({
    nombre: new FormControl(''),
    email: new FormControl('',  Validators.email),
    contrasenia: new FormControl(''),
    imagenUrl:new FormControl(''),
    sala:new FormControl("")
  });

  guardarCambiosCliente() {
    if (this.perfilForm.valid) {
      const datos = this.perfilForm.value;
      this.perfilServicio.actualizarPerfilCliente(datos).subscribe(() => {
        alert('Datos actualizados correctamente');
      });
    }
  }
 //Para mañana hacer el backend ,el admin y subirlo
  guardarCambiosAdmin(){

  }

}
