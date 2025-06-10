import { Component, OnInit } from '@angular/core';
import { HeaderWashabiComponent } from "../../shared/header-washabi/header-washabi.component";
import { FooterComponent } from "../../shared/footer/footer.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient,  } from '@angular/common/http';
import { PerfilService } from '../../core/services/perfil.service';
@Component({
  selector: 'app-chat',
  imports: [HeaderWashabiComponent, FooterComponent, CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent implements OnInit {

  constructor(private http: HttpClient,private perfilService:PerfilService) { }
  mensajes = [{ enviado:"",texto:""}];
  nombre=""
  nuevoMensaje = '';
 ngOnInit():void{
    this.perfilService.obtenerPerfil().subscribe(res=>{
      this.mensajes.push({enviado: 'bot', texto: `¡Hola! 😊 ${res.nombre} ¿En qué puedo ayudarte relacionado con las comidas?` })
     this.nombre=res.nombre;
    });
  };

  enviarMensaje() {
    var mensajeAEnviar=""
    if (this.nuevoMensaje.trim()) {
      this.mensajes.push({ enviado: "user", texto: this.nuevoMensaje })
      mensajeAEnviar="Buenas soy "+this.nombre+" y esta es mi pregunta:"+this.nuevoMensaje;
       this.nuevoMensaje = '';
      this.http.post<any>('http://localhost:8080/api/chatbot/mandarMensaje',{},{ params:{'message':mensajeAEnviar},  responseType: 'text' as 'json' }).subscribe({
        next: (respuesta) => {
          this.mensajes.push({ enviado: "bot", texto: respuesta })
        },

        error: () => {
          this.mensajes.push({
            texto: 'Lo siento, hubo un error al contactar con el asistente WashabiBot.',
            enviado: 'bot',
          });
        },
      })
    }
  }
}
