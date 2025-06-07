import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
export const noAuthGuard: CanActivateFn = (route, state) => {
    const token = localStorage.getItem('token');
  const router = inject(Router);
const snackBar = inject(MatSnackBar);
   if(token) {
     snackBar.open(
      `Bloqueado el acceso a ${state.url}, no puedes volverte a registrar o logearte una vez registrado.`,
      'Cerrar',
      { duration: 4000 }
    );
    router.navigate(['/perfil']);
   return false // acceso denegado  
   
  } else {
   
    return true// acceso permitido
  }

};
