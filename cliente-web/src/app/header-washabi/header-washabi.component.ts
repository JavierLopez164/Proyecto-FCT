import { Component,OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { PerfilService } from '../services/perfil.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header-washabi.component.html',
  styleUrl: './header-washabi.component.css'
})
export class HeaderWashabiComponent implements OnInit{

  prender=false;
  estaPulsado=false;
  rutaActual="";
  perfil=""
  constructor(private ruta: Router,private perfilService:PerfilService) {
    this.rutaActual = this.ruta.url;
  }

  ngOnInit(): void {
     this.perfilService.obtenerPerfil().subscribe(res=>{
      this.perfil =  res.fotoPerfil?.imagenUrl;})
  }
  estaLogeado():Boolean{
    return localStorage.getItem('token')!=null;
  }
  logout() {

   
    // Eliminar el token del localStorage
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    // Redirigir al login 
     this.ruta.navigate(['/login']);

     
  }
}
 