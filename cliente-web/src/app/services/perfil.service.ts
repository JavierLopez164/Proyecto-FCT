import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class PerfilService {
   apiUrl = 'http://localhost:8080/api/clientes/'; 
   token = localStorage.getItem('token')??"";
   emailGuardado=localStorage.getItem('email')??"";
 
  constructor(private http: HttpClient) {}
  actualizarPerfilCliente(datos: any): Observable<any> {
    return this.http.put(this.apiUrl, datos);
  }

  obtenerPerfil(): Observable<any> {
    const headers= new HttpHeaders({
      'Authorization': this.token, 'Content-Type': 'application/json',
  'Accept': 'application/json'
    });
    return this.http.get(this.apiUrl+"consultar/"+encodeURIComponent(this.emailGuardado),{headers});
  }
 
}
