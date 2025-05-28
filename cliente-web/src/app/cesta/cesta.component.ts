import { Component } from '@angular/core';
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";
import { FooterComponent } from "../footer/footer.component";
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule,FormsModule , } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';


@Component({
  selector: 'app-cesta',
  imports: [HeaderWashabiComponent, FooterComponent,MatIconModule,ReactiveFormsModule,FormsModule,MatFormFieldModule,MatInputModule],
  templateUrl: './cesta.component.html',
  styleUrl: './cesta.component.css'
})
export class CestaComponent {
carrito = [
  {
    id: '1',
    nombre: 'Hamburguesa Doble',
    precio: 8.99,
    cantidad: 1,
    imagen: 'https://okdiario.com/img/2024/10/06/comidas-rapidas-y-balanceadas_-resuelvelas-en-menos-de-20-minutos.jpg'
  },
  {
    id: '2',
    nombre: 'Papas Fritas',
    precio: 2.99,
    cantidad: 2,
    imagen: 'https://img.hellofresh.com/w_3840,q_auto,f_auto,c_fill,fl_lossy/hellofresh_s3/image/HF_Y24_R16_W42_ES_ESSGP30616-2_Main__edit_meat_high-a670615d.jpg'
  },
  // ...
];

  eliminarItem(id: string): void {
    this.carrito = this.carrito.filter(item => item.id !== id);
  }

  calcularTotal(): number {
    return this.carrito.reduce((total, item) => total + (item.precio * item.cantidad), 0);
  }

  finalizarPedido(): void {
    // Aquí simularías la lógica para pagar con Stripe u otro servicio
    console.log('Pedido finalizado:', this.carrito);
    console.log('Total: $' + this.calcularTotal().toFixed(2));
    alert('Redirigiendo al pago...');
    
  }
}

