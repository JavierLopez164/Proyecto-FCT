import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { PerfilService } from '../../core/services/perfil.service';
import { MatIconModule } from '@angular/material/icon';
import { CarritoService } from '../../core/services/carrito.service';
import { MatBadgeModule } from '@angular/material/badge';
@Component({
  selector: 'app-header',
  imports: [RouterLink, MatIconModule, MatBadgeModule],
  templateUrl: './header-washabi.component.html',
  styleUrl: './header-washabi.component.css'
})
export class HeaderWashabiComponent implements OnInit {


  prender = false;
  estaPulsado = false;
  rutaActual = "";
  perfil = ""
  constructor(private ruta: Router, private perfilService: PerfilService, private carrito: CarritoService) {
    this.rutaActual = this.ruta.url;
  }

  ngOnInit(): void {
    if(localStorage.getItem("token"))
    this.perfilService.obtenerPerfil().subscribe(res => {
      this.perfil = res.fotoPerfil?.imagenUrl;
    })

  }
  obtenerLogintudComida(): number {
    return this.carrito.comidasLongitud();
  }
  estaLogeado(): Boolean {
    return localStorage.getItem('token') != null;
  }
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    // Redirigir al login 
    this.ruta.navigate(['/login']);


  }
}
