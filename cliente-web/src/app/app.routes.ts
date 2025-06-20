import { Routes } from '@angular/router';
import { InicioComponent } from './features/inicio/inicio.component';
import { ChatComponent } from './features/chat/chat.component';
import { DescargarappComponent } from './features/descargarapp/descargarapp.component';
import { authGuard } from './core/guards/auth.guard';
import { noAuthGuard } from './core/guards/no-auth.guard';
import { GaleriaComponent } from './features/galeria/galeria.component';
import { MenuComponent } from './features/menu/menu.component';
import { CestaComponent } from './features/cesta/cesta.component';
import { AjusteComponent } from './core/auth/ajuste/ajuste.component';
import { LoginComponent } from './core/auth/login/login.component';
import { PerfilComponent } from './core/auth/perfil/perfil.component';
import { RegistrarComponent } from './core/auth/registrar/registrar.component';
export const routes: Routes = [
    {path:"",title:"Inicio",component:InicioComponent,},   
    {path:"galeria",title:"Galeria",component:GaleriaComponent,
     canActivate: [authGuard]  },//Middleware para que antes de ir a la ruta el authGuard me verifica si puede o no con el token
    {path:"chat",title:"Chat",component:ChatComponent,
        canActivate: [authGuard]},
    {path:"descargarapp",title:"Descarga de App móvil",component:DescargarappComponent},
    {path:"perfil",title:"Perfil",component:PerfilComponent,
        canActivate: [authGuard]},
    {path:"ajuste",title:"Ajuste de Perfil",component:AjusteComponent,
        canActivate: [authGuard]},
    {path:"registrar",title:"Registrar",component:RegistrarComponent,canActivate:[noAuthGuard]},
    {path:"login",title:"Login",component:LoginComponent,canActivate:[noAuthGuard]},
      {path:"menu",title:"Menu",component:MenuComponent,
        canActivate: [authGuard]},
          {path:"cesta",title:"Cesta",component:CestaComponent,
        canActivate: [authGuard]},
    ];
