import { Component, OnInit, signal } from '@angular/core';
import { HeaderWashabiComponent } from "../../shared/header-washabi/header-washabi.component";
import { FooterComponent } from "../../shared/footer/footer.component";
import { ReactiveFormsModule, FormControl, FormGroup, Validators, } from '@angular/forms';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { MatToolbar } from '@angular/material/toolbar';

import { RouterLink } from '@angular/router';

import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatStepperModule } from '@angular/material/stepper';
import { MatCardModule } from '@angular/material/card';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PerfilService } from '../../core/services/perfil.service';
@Component({
  selector: 'app-ajuste',
  imports: [HeaderWashabiComponent, FooterComponent, RouterLink, MatCardModule, MatIconModule, MatStepperModule, MatSnackBarModule, MatToolbar, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule,],
  templateUrl: './ajuste.component.html',
  styleUrl: './ajuste.component.css'
})
export class AjusteComponent implements OnInit {
  nombreArchivo: string = '';
  archivoSeleccionado: File | null = null;
  perfil = {
    email:"",
    nombre: "",
    contrasenia: "",
    rol:"",
    fechaCreacion:"",
    fotoPerfil:null,
    restaurante:""
  };
  constructor(private perfilServicio: PerfilService, private snackBar: MatSnackBar, private http: HttpClient) { }
  ngOnInit(): void {
    this.perfilServicio.obtenerPerfil().subscribe(
      res => {
        this.perfil.email=res.email;
        this.perfil.nombre = res.nombre;
        this.perfil.contrasenia = res.contrasenia;
        this.perfil.rol=res.rol;
        this.perfil.fechaCreacion=res.fechaCreacion;
        this.perfil.fotoPerfil=res.fotoPerfil;
        this.perfil.restaurante=res.restaurante;
      }
    )
  }

  perfilForm = new FormGroup({
    nombre: new FormControl(''),
    email: new FormControl('', Validators.email),
    contrasenia: new FormControl(''),
  });

  guardarCambiosCliente() {
    if (this.archivoSeleccionado != null) {
      const formData = new FormData();
      const headers = new HttpHeaders({
        'Authorization': localStorage.getItem('token') ?? "",
      });
      formData.append('imagenFichero', this.archivoSeleccionado);
      formData.append("email",this.perfil.email)
      this.http.post('http://localhost:8080/api/fotos/actualizarfotoperfil',formData,{headers}).subscribe({
        next: (res) => {
          this.snackBar.open('Ha sido actualizado tu foto de perfil', 'Cerrar', {
          duration: 3000
        });
        },
        error: (err) => {
          console.error('Error al subir la imagen', err);
          this.snackBar.open('Error al subir la imagen de perfil', 'Cerrar', {
            duration: 3000,
          });
        }
      });
    }



    if (this.perfilForm.valid) {
      const perfilActualizar: any = this.perfil

      const nombre = this.perfilForm.get('nombre')?.value;
      if (nombre) perfilActualizar.nombre = nombre;

      const contrasenia = this.perfilForm.get('contrasenia')?.value;
      if (contrasenia) perfilActualizar.contrasenia = contrasenia;

      this.perfilServicio.actualizarPerfilCliente(perfilActualizar).subscribe(res => {
        this.snackBar.open('Ha sido actualizado tu perfil', 'Cerrar', {
          duration: 3000
        });
      });
    }
  }


  enArchivoSeleccionado(event: Event): void {
    //El tarjet y el as htmlinput es como castear el evento que viene por subir una foto
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.archivoSeleccionado = input.files[0];
      this.nombreArchivo = this.archivoSeleccionado.name;
    }
  }
}
