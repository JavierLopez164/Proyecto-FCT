import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header-washabi.component.html',
  styleUrl: './header-washabi.component.css'
})
export class HeaderWashabiComponent {

  prender=false;
  estaPulsado=false;
  rutaActual="";
  constructor(private ruta: Router) {
    this.rutaActual = this.ruta.url;
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
 