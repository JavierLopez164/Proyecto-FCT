import { Routes } from '@angular/router';
import { ReservaComponent } from './reserva/reserva.component';
import { InicioComponent } from './inicio/inicio.component';
import { ChatComponent } from './chat/chat.component';
import { MenuComponent } from './menu/menu.component';
import { PerfilComponent } from './perfil/perfil.component';
import { AjusteComponent } from './ajuste/ajuste.component';
import { DescargarappComponent } from './descargarapp/descargarapp.component';
import { RegistrarComponent } from './registrar/registrar.component';
import { LoginComponent } from './login/login.component';
import { authGuard } from './auth.guard';
import { noAuthGuard } from './guards/no-auth.guard';
export const routes: Routes = [
    {path:"",title:"Inicio",component:InicioComponent,},   
    {path:"reserva",title:"Reserva",component:ReservaComponent,
        canActivate: [authGuard]},//Middleware para que antes de ir a la ruta el authGuard me verifica si puede o no con el token
    {path:"chat",title:"Chat",component:ChatComponent,
        canActivate: [authGuard]},
    {path:"menu",title:"Menú Comida",component:MenuComponent},
    {path:"descargarapp",title:"Descarga de App móvil",component:DescargarappComponent},
    {path:"perfil",title:"Perfil",component:PerfilComponent,
        canActivate: [authGuard]},
    {path:"ajuste",title:"Ajuste de Perfil",component:AjusteComponent,
        canActivate: [authGuard]},
    {path:"registrar",title:"Registrar",component:RegistrarComponent,canActivate:[noAuthGuard]},
    {path:"login",title:"Login",component:LoginComponent,canActivate:[noAuthGuard]},
];
