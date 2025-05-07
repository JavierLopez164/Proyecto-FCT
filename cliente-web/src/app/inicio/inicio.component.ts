import { Component } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";

@Component({
  selector: 'app-inicio',
  imports: [FooterComponent, HeaderWashabiComponent],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent {

}
