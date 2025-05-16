import { Component, OnInit, signal } from '@angular/core';
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";
import { FooterComponent } from "../footer/footer.component";
import { ReactiveFormsModule, FormControl, FormGroup, Validators, } from '@angular/forms';
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
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
@Component({
  selector: 'app-ajuste',
  imports: [HeaderWashabiComponent, FooterComponent, RouterLink, MatIconModule, MatSnackBarModule,MatExpansionModule, MatIcon, MatExpansionPanelTitle, MatToolbar, MatExpansionPanel, MatExpansionPanelHeader, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule,],
  templateUrl: './ajuste.component.html',
  styleUrl: './ajuste.component.css'
})
export class AjusteComponent implements OnInit {
  perfil = {
    nombre: "",
    email: "",
    contrasenia: "",
    sala: "",
    rol: "",
    fechaCreacion: "",
    imagenUrl:""
  };
  readonly panelOpenState = signal(false);
  constructor(private perfilServicio: PerfilService,private snackBar: MatSnackBar) { }
  ngOnInit(): void {
    this.perfilServicio.obtenerPerfil().subscribe(
      res => {
      this.perfil.nombre = res.nombre;
      this.perfil.email = res.email;
      this.perfil.rol = res.rol;
      this.perfil.contrasenia=res.contrasenia
      this.perfil.fechaCreacion = res.fechaCreacion;
      this.perfil.imagenUrl=res.imagenUrl;
        this.panelOpenState.set(res.rol == 'ROLE_USER');
      }
    )
  }

  perfilForm = new FormGroup({
    nombre: new FormControl(''),
    email: new FormControl('', Validators.email),
    contrasenia: new FormControl(''),
    imagenUrl: new FormControl(''),
    sala: new FormControl("")
  });

  guardarCambiosCliente() {
    if (this.perfilForm.valid) {
       const perfilActualizar: any =this.perfil

    const nombre = this.perfilForm.get('nombre')?.value;
    if (nombre) perfilActualizar.nombre = nombre;

    const contrasenia = this.perfilForm.get('contrasenia')?.value;
    if (contrasenia) perfilActualizar.contrasenia =  contrasenia;

    const imagenUrl = this.perfilForm.get('imagenUrl')?.value;
    if (imagenUrl) perfilActualizar.imagenUrl =  imagenUrl;

      this.perfilServicio.actualizarPerfilCliente(perfilActualizar).subscribe(res => {
        console.log(res)
        this.snackBar.open('Ha sido actualizado tu perfil', 'Cerrar', {
        duration: 3000
      });
      });
    }
  }
  //Para mañana hacer el backend ,el admin y subirlo
  guardarCambiosAdmin() {

  }

}
