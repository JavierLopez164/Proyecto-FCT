import { AfterViewInit, Component, OnInit, inject, ViewChild, ElementRef } from '@angular/core';
import { HeaderWashabiComponent } from "../../shared/header-washabi/header-washabi.component";
import { FooterComponent } from "../../shared/footer/footer.component";
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule, FormsModule, } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatBadgeModule } from '@angular/material/badge';
import { CarritoService } from '../../core/services/carrito.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { loadStripe, Stripe, StripeElements, StripeCardElement } from '@stripe/stripe-js';
import { MatStepperModule } from '@angular/material/stepper';

@Component({
  selector: 'app-cesta',
  imports: [HeaderWashabiComponent, FooterComponent, MatStepperModule, MatIconModule, MatBadgeModule, ReactiveFormsModule, FormsModule, MatFormFieldModule, MatInputModule],
  templateUrl: './cesta.component.html',
  styleUrl: './cesta.component.css'
})

export class CestaComponent implements OnInit, AfterViewInit {

  puedePagar = false;
  comidas: any[] = [];
  estaEnElMismoRestaurante = false;
  constructor(private http: HttpClient, private carrito: CarritoService) { }

  @ViewChild('cardElementRef') cardElementRef!: ElementRef;
  stripe!: Stripe;
  elements!: StripeElements;
  card!: StripeCardElement;
  stripeMontado = false;
  mostrarStepper = false;
  onStepChange(event: any) {
    if (event.selectedIndex === 1 && !this.stripeMontado) {
      this.ngAfterViewInit();
    }
  }
  async ngAfterViewInit() {

    const stripeInstance = await loadStripe('pk_test_51RRZ062cdmCzxJrf4ullXjgQ8CRmXXhGxDZ9rJTc1hmV2J91kzAGeBJBKzSdgKlzE6N7BBMQJN6IX98kwhXoyT0E00DoxIWgOx');

    if (!stripeInstance) {
      console.error('No se pudo cargar Stripe.');
      return;
    }
    this.stripe = stripeInstance;
    this.elements = this.stripe.elements();
    this.card = this.elements.create('card', {
      style: {
        base: {
          color: '#1a202c', // gris oscuro
          fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
          fontSize: '16px',
          '::placeholder': {
            color: '#a0aec0', // gris claro
          },
        },
        invalid: {
          color: '#e53e3e', // rojo
        },
      }
    });

    this.card.mount(this.cardElementRef.nativeElement);
    this.stripeMontado = true;

  }
  ngOnInit(): void {

    this.carrito.cesta$.subscribe(data => {
      if (data != null)
        this.comidas = data;
    });

    this.obtenerPedidoActivoDeRestaurante();
    if (this.carrito.obtenerNombreRestaurante() != "")
      this.estaEnElMismoRestaurante = this.carrito.obtenerNombreRestaurante() == this.carrito.obtenerPedidoActual().restaurante



    
    const pedidoActual = this.carrito.obtenerPedidoActual();

    if (pedidoActual != null) {
      const comidasActuales: any[] = pedidoActual.items;

      comidasActuales.forEach(c => {
        const nombre = c.comida.comidaPK?.ncomida;
        const restaurante = c.comidaPK?.nrestaurante;
        const cantidad = c.cantidad;
   
        const itemEnCesta = this.carrito.obtenerCesta().find(item =>
          item.nombre == nombre &&
          item.restaurante == restaurante &&
          item.cantidad == cantidad
        );

   
        if (!itemEnCesta) {
          this.carrito.insertarCesta({
            restaurante: restaurante,
            nombre: nombre,
            precio: c.comida.price,
            imagen: c.comida.foto?.imagenUrl,
            descripcion: c.comida.description,
            category: c.comida.category,
            preparationTime: c.comida.preparationTime,
            attributes: c.comida.attributes,
            features: c.comida.features,
            cantidad: cantidad, 
          });

  
          this.carrito.marcarComoEnviada({
            nombre: nombre,
            restaurante: restaurante,
            cantidad: cantidad
          });
        }
      });
    }




  }

  obtenerPedidoActivoDeRestaurante() {

    this.http.get<any>('http://localhost:8080/api/pedidos/encontrarpedidoactivorestaurante', { params: { email: localStorage.getItem('email') ?? "" } }).subscribe({
      next: (respuesta) => {
        this.carrito.establecerPedidoCreado(respuesta);

        this.puedePagar = true;
      }
    })

  }


  // Eliminar un item por id
  eliminarItem(id: number): void {
    this.carrito.eliminarFromCesta(id);
  }

  // Calcular el total
  calcularTotal(): number {
    return this.carrito.calcularTotal();
  }

  // Finalizar pedido
  finalizarPedido(): void {

    const comidasNuevas = this.carrito.obtenerCesta().filter(c => this.carrito.esComidaNueva(c));

    comidasNuevas.forEach(comida => {
      this.http.post<any>('http://localhost:8080/api/pedidos/aniadircomidas', {}, {
        params: {
          'pedidoId': this.carrito.obtenerPedidoActual().id,
          "nComida": comida.nombre,
          "nRestaurante": comida.restaurante,
          "cantidad": comida.cantidad,
          "total": this.carrito.calcularTotal()
        }
      }).subscribe({
        next: () => {
          // Marca como enviada solo si el POST fue exitoso
          this.carrito.marcarComoEnviada(comida);
        },
        error: (error) => {
          console.error('Error al añadir la comida al pedido:', error);
        }
      });
    });


    this.mostrarStepper = true;
  }
  limpiarCesta(): void {
    this.mostrarStepper = false;
    this.carrito.limpiarCesta();
  }

  form = {
    description: '',
    amount: 0,
    currency: 'EUR',
    address: '',
    email: ""
  };

  mensajeErrorPago: string = '';
  paymentResponse: {
    id: string;
    status: string;
    clientSecret: string;
  } | null = null;
  pagoExitoso: boolean | null = null;
  pagarConStripe(): void {

    this.stripe.createPaymentMethod({
      type: 'card',
      card: this.card,
      billing_details: {
        address: {
          line1: this.form.address || undefined
        }
      }
    }).then((resultado: any) => {
      if (resultado.error) {
        const mostrarError = document.getElementById('card-errors');
        if (mostrarError) {
          mostrarError.textContent = resultado.error.message;
        }
        this.pagoExitoso = false;
        this.mensajeErrorPago = resultado.error.message;
      } else {

        const dto = {
          description: this.form.description,
          amount: this.form.amount * 100,
          currency: this.form.currency,
          paymentMethodId: resultado.paymentMethod.id,
          correo: this.form.email
        };

        this.http.post<any>('http://localhost:8080/api/stripe/paymentintent', dto)
          .subscribe({
            next: (data) => {
              // Guardamos la respuesta del backend en la propiedad
              this.paymentResponse = {
                id: data.id,
                status: data.status,
                clientSecret: data.clientSecret
              };
              this.pagoExitoso = true;
            },
            error: (err) => {
              this.pagoExitoso = false;
              this.mensajeErrorPago = 'Error al crear PaymentIntent.';
              console.error('Error creando PaymentIntent:', err);
            }
          });



        this.http.put<any>('http://localhost:8080/api/pedidos/cambiar-estado', {}, {
          params: {
            'id': this.carrito.obtenerPedidoActual().id,
            'activo': false
          }
        }).subscribe({
          next: (respuesta) => {

          },
          error: (error) => {
            console.error('Error al cambiar estado del pedido:', error);
          }
        });


      }
    });

  }
}
