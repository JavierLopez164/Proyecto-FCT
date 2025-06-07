import { Component } from '@angular/core';
import { FooterComponent } from "../../shared/footer/footer.component";
import { HeaderWashabiComponent } from "../../shared/header-washabi/header-washabi.component";
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatExpansionModule } from '@angular/material/expansion';
@Component({
  selector: 'app-descargarapp',
  imports: [FooterComponent, HeaderWashabiComponent, MatButtonModule,MatToolbarModule,  MatExpansionModule],
  templateUrl: './descargarapp.component.html',
  styleUrl: './descargarapp.component.css'
})
export class DescargarappComponent {

}
