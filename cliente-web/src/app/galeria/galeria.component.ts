import { Component, OnInit } from '@angular/core';
import { HeaderWashabiComponent } from '../header-washabi/header-washabi.component';
import { FooterComponent } from '../footer/footer.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-galeria',
  imports: [CommonModule, HeaderWashabiComponent, MatIconModule,FooterComponent, MatFormFieldModule, MatSelectModule, MatOptionModule, MatCardModule],
  templateUrl: './galeria.component.html',
  styleUrl: './galeria.component.css'
})


export class GaleriaComponent implements OnInit {

  comidas: any[] = [];
  comidasFiltradas: any[] = [];
  nombresRestaurantes: string[] = [];
  restauranteSeleccionado: string = 'todos';
  ordenarPor: string = '';
  private urlComida = 'http://localhost:8080/api/comida';


  private headers = new HttpHeaders({
    Authorization: localStorage.getItem('token') ?? ''
  });

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.cargarComidas();
    this.cargarNombresDeRestaurantes();
  }

  // Carga las comidas desde el backend
  cargarComidas(): void {
    this.http.get<any[]>(`${this.urlComida}/listarComidas`, { headers: this.headers })
      .subscribe({
        next: (comidas) => {
          this.comidas = comidas.map(c => ({
            restaurante: c.comidaPK?.nrestaurante,
            nombre: c.comidaPK?.ncomida,
            descripcion: c.description,
            precio: c.price,
            categoria: c.category,
            tiempoPreparacion: c.preparationTime,
            imagen: c.foto?.imagenUrl,
            fechaSubida:new Date(c.foto?.fecha)
          }));
          this.aplicarFiltros();
        },
        error: err => console.error('Error al listar comidas', err)
      });
  }
    // Carga la lista de nombres de restaurantes
  cargarNombresDeRestaurantes(): void {
    this.http.get<string[]>(`${this.urlComida}/obtenerNombresRestaurante`, { headers: this.headers })
      .subscribe({
        next: (nombres) => {
          this.nombresRestaurantes = ['todos', ...nombres];
        },
        error: err => console.error('Error al cargar nombres de restaurantes', err)
      });
  }

  // Aplica filtros de restaurante y orden
  aplicarFiltros(): void {
    let resultado = [...this.comidas];

    // Filtrar por restaurante
    if (this.restauranteSeleccionado !== 'todos') {
      resultado = resultado.filter(c => c.restaurante == this.restauranteSeleccionado);
    }

    // Ordenar según selección
    switch (this.ordenarPor) {
      case 'fechaReciente':
        resultado.sort((a, b) => b.fechaSubida.getTime() - a.fechaSubida.getTime());
        break;
      case 'fechaAntigua':
        resultado.sort((a, b) => a.fechaSubida.getTime() - b.fechaSubida.getTime());
        break;
      default:
        resultado.sort((a, b) => a.nombre.localeCompare(b.nombre));
        break;
    }

    this.comidasFiltradas = resultado;
  }

  // Cambia el criterio de ordenamiento
  alCambiarOrden(evento: any): void {
    this.ordenarPor = evento.value;
    this.aplicarFiltros();
  }

  // Cambia el filtro de restaurante
  alCambiarRestaurante(evento: any): void {
    this.restauranteSeleccionado = evento.value;
    this.aplicarFiltros();
  }

}
