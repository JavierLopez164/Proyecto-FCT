import { Component } from '@angular/core';
import { HeaderWashabiComponent } from "../../shared/header-washabi/header-washabi.component";
import { FooterComponent } from "../../shared/footer/footer.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chat',
  imports: [HeaderWashabiComponent, FooterComponent,CommonModule,FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent {
  messages = [
    { id:1,enviado: 'bot', texto: '¡Hola! ¿En qué puedo ayudarte?' },
    { id:2,enviado: 'user', texto: 'Tengo una duda sobre mi pedido.' },
  ];
  newMessage = '';

  sendMessage() {
    if (this.newMessage.trim()) {
      this.messages.push({ id:1,enviado: 'user', texto: this.newMessage });
      this.newMessage = '';
      setTimeout(() => {
        this.messages.push({id:2, enviado: 'bot', texto: 'Gracias, te respondemos pronto.' });
      }, 1000);
    }
  }
}
