import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
 
  const token = localStorage.getItem('token');
  const router = inject(Router);
  //Tengo que hacer los dos return porque sino es mas complejo el hacerlo por uno porque tendria que llamar a varias clases
   if(token) {
   return true // acceso permitido  
  } else {
   alert(`Bloqueado el acceso a ${state.url},para acceder a la ruta protegida (inicia sesión)`);
    router.navigate(['/login']);
    return false// acceso denegado
  }

 
};
