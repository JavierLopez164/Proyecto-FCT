import { Component, OnInit } from '@angular/core';
import { HeaderWashabiComponent } from '../header-washabi/header-washabi.component';
import { FooterComponent } from '../footer/footer.component';
import { MatSelectModule } from '@angular/material/select';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { MatOptionModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CarritoService } from '../services/carrito.service';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-menu',
  imports: [HeaderWashabiComponent, FooterComponent, MatSelectModule, CommonModule, ReactiveFormsModule, MatOptionModule, MatInputModule, MatButtonModule, MatIconModule, MatCardModule, MatExpansionModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit {

  constructor(private http: HttpClient, private carrito: CarritoService, private snackBar: MatSnackBar) { }
  ngOnInit(): void {
    this.cargarNombresDeRestaurantes();
  }

  panelAbierto: string | null = null;
  restauranteSeleccionado: string = ''
  nombresRestaurantes: string[] = [];
  comidas: any[] = [];
  nombreComida: string = ""
  puedeComentarPorComida: Record<string, boolean> = {};
  mediaPuntuacion: Record<string, number> = {};
  comentariosPorComida: Record<string, any[]> = {}
  private urlComida = 'http://localhost:8080/api/comida';
  private urlComentario = 'http://localhost:8080/api/comentarios';
  private headers = new HttpHeaders({
    Authorization: localStorage.getItem('token') ?? '', 'Content-Type': 'application/json',
  });


  cargarComidaPorRestaurante(): void {
    if (this.restauranteSeleccionado != "") {
      this.http.post<any[]>(`${this.urlComida}/obtenerPorRestaurante`, this.restauranteSeleccionado, { headers: this.headers })
        .subscribe({
          next: (comidas) => {
            this.comidas = comidas.map(c => ({
              restaurante: c.comidaPK?.nrestaurante,
              nombre: c.comidaPK?.ncomida,
              precio: c.price,
              imagen: c.foto?.imagenUrl,
              descripcion: c.description,
              category: c.category,
              preparationTime: c.preparationTime,
              attributes: c.attributes,
              features: c.features,
              cantidad: 1,
            }));
          },
          error: err => console.error('Error al cargar comidas', err)
        });
    }
  }

  cargarNombresDeRestaurantes(): void {
    this.http.get<string[]>(`${this.urlComida}/obtenerNombresRestaurante`, { headers: this.headers })
      .subscribe({
        next: (nombres) => {
          //Te crea una copia de ese array
          this.nombresRestaurantes = [...nombres];
        },
        error: err => console.error('Error al cargar nombres de restaurantes', err)
      });
  }
  cargarMediaValoracion(comida: string) {

    const estaCargada = this.mediaPuntuacion[comida] !== undefined;

    if (!estaCargada) {
      this.http.get<number>(this.urlComentario + "/promedio", {
        params: {
          comida: comida,
          restaurante: this.restauranteSeleccionado
        }
      }).subscribe({
        next: (media) => {
          this.mediaPuntuacion[comida] = media;
        },
        error: (err) => {
          console.error('Error al mostrar promedio comentarios', err);
        }
      });
    }
  }
  alCambiarRestaurante(evento: any): void {
    this.restauranteSeleccionado = evento.value;
    this.comentariosPorComida = {}
    this.mediaPuntuacion = {}
    this.puedeComentarPorComida={}
    this.cargarComidaPorRestaurante();

  }


    puedeInsertarComentario(comida: string) {
    if(this.puedeComentarPorComida[comida] == undefined){
    this.http.get<boolean>(this.urlComentario + "/puede-comentar", {
      headers: this.headers,
      params: {
        comida: comida,
        restaurante: this.restauranteSeleccionado
      }
    }).subscribe({
      next: (response) => {
        this.puedeComentarPorComida[comida] = response;
        console.log(this.puedeComentarPorComida)
      },
      error: () => {
        this.puedeComentarPorComida[comida] = false; 
      }
    });
    }
  }

  alAbrirPanel(evento: any) {
    this.nombreComida = evento
    this.panelAbierto = evento;
      this.puedeInsertarComentario(evento);
    if (!this.comentariosPorComida[evento]) {
      this.http.get<any[]>(this.urlComentario + "/lista", {
        params: {
          comida: this.nombreComida,
          restaurante: this.restauranteSeleccionado
        }
      }).subscribe({
        next: (comentarios) => {

          this.comentariosPorComida[this.nombreComida] = comentarios.map(co => ({
            contenido: co.contenido,
            valoracion: co.valoracion,
            emailCli: co.clienteEmail
          })
          )

        }
        ,
        error: (err) => {
          console.error('Error al listar comentarios', err);
        }
      })

    }

  }
  puntuacion: number = -1;
  comentarioForm = new FormGroup({
    contenido: new FormControl('', Validators.required),

  });

  alCerrarPanel(nombreComida: string) {
    // Solo cerrar si el panel cerrado es el abierto actualmente
    if (this.panelAbierto == nombreComida) {
      this.panelAbierto = null;
    }
  }
  setPuntuacion(valor: number) {
    console.log(valor)
    this.puntuacion = valor;
  }
  comentarioHecho = false;
  insertarComentario() {

    const comentario = {
      contenido: this.comentarioForm.get("contenido")?.value,
      valoracion: this.puntuacion,
    };

    this.http.post<any>(this.urlComentario + "/crear", comentario, {
      headers: this.headers,
      params: {
        comida: this.nombreComida,
        restaurante: this.restauranteSeleccionado
      }
    },).subscribe({
      next: (nuevoComentario) => {

        this.comentariosPorComida[this.nombreComida].push(nuevoComentario);
        this.comentarioForm.disable();
        this.comentarioHecho = true
        console.log("comentario insertado");
      },
      error: (err) => {
        console.error('Error al guardar comentario', err);
      }
    });
  }
  insertarAlCarrito(comida: any) {
    const restauranteEnCarrito = this.carrito.obtenerNombreRestaurante() || this.restauranteSeleccionado;
    const mismoRestaurante = restauranteEnCarrito == this.restauranteSeleccionado;
    const mensaje = mismoRestaurante
      ? `Añadido al carrito la comida: ${comida.nombre}`
      : 'Se ha quitado el pedido anterior del restaurante anterior';

    this.snackBar.open(mensaje, 'Cerrar', {
      duration: 3000,
    });
    this.carrito.insertarCesta(comida);
  }
}

