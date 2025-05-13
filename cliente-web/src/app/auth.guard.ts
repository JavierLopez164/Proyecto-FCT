import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

export const authGuard: CanActivateFn = (route, state) => {

  const token = localStorage.getItem('token');
  const router = inject(Router);
  const snackBar = inject(MatSnackBar);
   if(token) {
   return true // acceso permitido  
   
  } else {
   snackBar.open(
      `Bloqueado el acceso a ${state.url}, inicia sesión para acceder.`,
      'Cerrar',
      { duration: 4000}
    );
    router.navigate(['/login']);
    return false// acceso denegado
  }

 
};
