import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {

  private pedidoCreadoSubject = new BehaviorSubject<any | null>(null);

  // Observable expuesto para que otros componentes escuchen cambios
  pedidoCreado$ = this.pedidoCreadoSubject.asObservable();

  // Método para establecer el pedido actual
  establecerPedidoCreado(pedido: any) {
    this.pedidoCreadoSubject.next(pedido);
  }

  // Método para obtener el valor actual
  obtenerPedidoActual(): any {
    return this.pedidoCreadoSubject.getValue();
  }

  constructor() { }

  private cestaFuente = new BehaviorSubject<any[]>([]);
  cesta$ = this.cestaFuente.asObservable();
  obtenerCesta() {
    return this.cestaFuente.getValue();
  }
  insertarCesta(producto: any) {
    const cestaActual = this.cestaFuente.getValue();

    // Verificar si hay productos de otro restaurante
    const restaurantesEnCesta = new Set(cestaActual.map(item => item.restaurante));

    if (restaurantesEnCesta.size > 0 && !restaurantesEnCesta.has(producto.restaurante)) {
      // Si la cesta tiene productos de otro restaurante, la limpiamos
      this.cestaFuente.next([]);
    }

    // Obtener la cesta actualizada tras limpiar (o no)
    const cestaActualizada = this.cestaFuente.getValue();

    // Buscar si el producto ya está en la cesta
    const index = cestaActualizada.findIndex(item => item.nombre === producto.nombre);

    if (index !== -1) {
      // Producto ya existe: aumentar la cantidad
      cestaActualizada[index].cantidad = (cestaActualizada[index].cantidad || 1) + 1;
      this.cestaFuente.next([...cestaActualizada]);
    } else {
      // Producto no existe: añadir con cantidad = 1
      const nuevoProducto = { ...producto, cantidad: 1 };
      this.cestaFuente.next([...cestaActualizada, nuevoProducto]);
    }
  }
  comidasLongitud(): number {
    return this.cestaFuente.getValue().length;
  }

  eliminarFromCesta(index: number) {
    const actualCesta = this.cestaFuente.getValue();
    actualCesta.splice(index, 1);
    this.cestaFuente.next([...actualCesta]);

  }

  limpiarCesta() {
    this.cestaFuente.next([]);
  }
  calcularTotal(): number {
    const cestaActual = this.cestaFuente.getValue();
    return cestaActual.reduce((total, item) => total + (item.precio * (item.cantidad || 1)), 0);
  }
  obtenerNombreRestaurante(): string {
  const cestaActual = this.cestaFuente.getValue();
  return cestaActual.length > 0 ? cestaActual[0].restaurante : '';
}
 
}
