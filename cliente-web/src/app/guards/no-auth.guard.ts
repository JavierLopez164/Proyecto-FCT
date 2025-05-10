import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
export const noAuthGuard: CanActivateFn = (route, state) => {
    const token = localStorage.getItem('token');
  const router = inject(Router);

   if(token) {
    router.navigate(['/perfil']);
   return false // acceso denegado  
   
  } else {
   
    return true// acceso permitido
  }

};
