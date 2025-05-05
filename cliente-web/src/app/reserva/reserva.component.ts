import { Component } from '@angular/core';
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";
import { FooterComponent } from "../footer/footer.component";

@Component({
  selector: 'app-reserva',
  imports: [HeaderWashabiComponent, FooterComponent],
  templateUrl: './reserva.component.html',
  styleUrl: './reserva.component.css'
})
export class ReservaComponent {

}
