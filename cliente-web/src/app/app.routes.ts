import { Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';
import { ChatComponent } from './chat/chat.component';
import { PerfilComponent } from './perfil/perfil.component';
import { AjusteComponent } from './ajuste/ajuste.component';
import { DescargarappComponent } from './descargarapp/descargarapp.component';
import { RegistrarComponent } from './registrar/registrar.component';
import { LoginComponent } from './login/login.component';
import { authGuard } from './auth.guard';
import { noAuthGuard } from './guards/no-auth.guard';
import { GaleriaComponent } from './galeria/galeria.component';
import { MenuComponent } from './menu/menu.component';
import { CestaComponent } from './cesta/cesta.component';
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
