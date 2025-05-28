import { Component, OnInit } from '@angular/core';
import { HeaderWashabiComponent } from '../header-washabi/header-washabi.component';
import { FooterComponent } from '../footer/footer.component';
import { MatSelectModule } from '@angular/material/select';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatOptionModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-menu',
  imports: [HeaderWashabiComponent, FooterComponent,MatSelectModule, CommonModule, ReactiveFormsModule, MatOptionModule, MatInputModule, MatButtonModule, MatIconModule, MatCardModule, MatExpansionModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit {

  constructor(private http: HttpClient) { }
  ngOnInit(): void {
    this.cargarNombresDeRestaurantes();
  }
  panelAbierto: string | null = null;
  restauranteSeleccionado: string = '';
  nombresRestaurantes: string[] = [];
  comidas: any[] = [];
  comentarios: any[] = [];
  nombreComida:string=""
  mediaPuntuacion :Record<string, number>= {};
  private urlComida = 'http://localhost:8080/api/comida';
  private urlComentario = 'http://localhost:8080/api/comentarios';
  private headers = new HttpHeaders({
    Authorization: localStorage.getItem('token') ?? '','Content-Type': 'application/json',
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
  cargarMediaValoracion(comida:string){
   
        this.http.get<number>(this.urlComentario + "/promedio", {
          params:{comida:comida,restaurante:this.restauranteSeleccionado},  
          
        }).subscribe({
       next: (media) => { 
           this.mediaPuntuacion[comida]=media
       },
      error: (err) => {
        console.error('Error al mostrar promedio comentarios', err);
      }
       })
      
  }
  alCambiarRestaurante(evento: any): void {
    this.restauranteSeleccionado = evento.value;
    this.cargarComidaPorRestaurante();
  }

  alAbrirPanel(evento: any) {
    this.nombreComida=evento
   
    this.http.get<any[]>(this.urlComentario + "/lista", {
      params: {
        comida: this.nombreComida,
        restaurante: this.restauranteSeleccionado
      }
    }).subscribe({
      next: (comentarios) => {
     
        this.comentarios = comentarios.map(co => ({
          contenido: co.contenido,
          valoracion: co.valoracion,
          emailCli:co.clienteEmail
        })
        )
        
      }
      ,
      error: (err) => {
        console.error('Error al listar comentarios', err);
      }
    })
  this.panelAbierto = evento;
  }
  puntuacion:number=-1; 
  comentarioForm = new FormGroup({
    contenido: new FormControl('',Validators.required),

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

  insertarComentario() {
    
  const comentario = {
    contenido: this.comentarioForm.get("contenido")?.value,
    valoracion:this.puntuacion,
  };
    
    this.http.post<any>(this.urlComentario + "/crear",comentario, {
      headers: this.headers,
      params: {
        comida: this.nombreComida,
        restaurante: this.restauranteSeleccionado
      }
    },).subscribe({
      next: (nuevoComentario) => {
        this.comentarios.push(nuevoComentario);
        console.log("comentario insertado");
      },
      error: (err) => {
        console.error('Error al guardar comentario', err);
      }
    });

  }
    
}

