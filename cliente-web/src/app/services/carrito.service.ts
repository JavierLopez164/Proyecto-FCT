import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
 private cestaFuente = new BehaviorSubject<any[]>([]);
  cesta$ = this.cestaFuente.asObservable();
  constructor() { }
 
  obtenerCesta() {
    return this.cestaFuente.getValue();
  }

  insertarCesta(producto: any) {
    
    this.cestaFuente.next([ this.cestaFuente.getValue(), producto]);
  }

  eliminarFromCesta(index: number) {
    const actualCesta = this.cestaFuente.getValue();
    actualCesta.splice(index, 1);
    this.cestaFuente.next([...actualCesta]);
  }

  limpiarCesta() {
    this.cestaFuente.next([]);
  }


}
