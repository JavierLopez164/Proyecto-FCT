import { Component, Input, SimpleChanges,OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-notificacion',
  imports: [CommonModule],
  templateUrl: './notificacion.component.html',
  styleUrl: './notificacion.component.css'
})
export class NotificacionComponent implements OnChanges {
  @Input() mensaje: string = '';
  @Input() tipo: 'exito' | 'error' = 'exito';
  mostrarNotificacion: boolean = true;

  private duracion = 3000; // 3 segundos
  private timeout: any;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['mensaje'] && this.mensaje) {
      this.mostrarNotificacion = true;

      // Si hay un timeout anterior, lo limpiamos
      if (this.timeout) clearTimeout(this.timeout);

      // Ocultar después de 3 segundos
      this.timeout = setTimeout(() => {
        this.mostrarNotificacion = false;
      }, this.duracion);
    }
  }

  cerrarManual(): void {
    this.mostrarNotificacion = false;
    if (this.timeout) clearTimeout(this.timeout);
  }
}
