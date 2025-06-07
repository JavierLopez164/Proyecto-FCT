import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class PerfilService {
   apiUrl = 'http://localhost:8080/api/clientes/'; 
 
  constructor(private http: HttpClient) {}
  actualizarPerfilCliente(datos: any): Observable<any> {
      const headers= new HttpHeaders({
      'Authorization': localStorage.getItem('token')??"", 'Content-Type': 'application/json',
  'Accept': 'application/json'
    });
    return this.http.put(this.apiUrl+"actualizar",datos,{headers});
  }

  obtenerPerfil(): Observable<any> {
    
    const headers= new HttpHeaders({
      'Authorization': localStorage.getItem('token')??"", 'Content-Type': 'application/json',
  'Accept': 'application/json'
    });
    return this.http.get(this.apiUrl+"consultar/"+encodeURIComponent(localStorage.getItem('email')??""),{headers});
  }
}
