import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../shared/footer/footer.component";
import { HeaderWashabiComponent } from "../../shared/header-washabi/header-washabi.component";
import { HttpClient } from '@angular/common/http';
import {  MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-inicio',
  imports: [FooterComponent, HeaderWashabiComponent, MatSelectModule, MatOptionModule, MatCardModule],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent implements OnInit {
  nombresRestaurantes: string[] = [];
  private urlComida = 'http://localhost:8080/api/comida';
  comidas: any[] = []
  comidasRestaurante:any[]=[]
  constructor(private http: HttpClient) { }
  ngOnInit(): void {
    this.cargarTop5Comidas();
     this.cargarNombresDeRestaurantes();
  }
  alCambiarRestaurante(evento:any) {
    this.http.get<any[]>('http://localhost:8080/api/pedidos/top5-comidas',{params:{restaurante:evento.value}}).subscribe({
      next: (respuesta) => {
        this.comidasRestaurante = respuesta.map(c => ({
          nombreComida: c?.nombreComida,
          restaurante: c?.restaurante,
          cantidad: c?.cantidadTotal
        }))

      }
    })

  }
cargarNombresDeRestaurantes(): void {
    this.http.get<string[]>(`${this.urlComida}/obtenerNombresRestaurante`)
      .subscribe({
        next: (nombres) => {
          this.nombresRestaurantes = [...nombres];
        },
        error: err => console.error('Error al cargar nombres de restaurantes', err)
      });
  }
  cargarTop5Comidas(): void {
    this.http.get<any[]>('http://localhost:8080/api/pedidos/top5-comidas/todos', {}).subscribe({
      next: (respuesta) => {
        this.comidas = respuesta.map(c => ({
          nombreComida: c?.nombreComida,
          restaurante: c?.restaurante,
          cantidad: c?.cantidadTotal
        }))

      }
    })


  }

}


