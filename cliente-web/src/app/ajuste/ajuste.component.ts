import { Component } from '@angular/core';
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";
import { FooterComponent } from "../footer/footer.component";

@Component({
  selector: 'app-ajuste',
  imports: [HeaderWashabiComponent, FooterComponent],
  templateUrl: './ajuste.component.html',
  styleUrl: './ajuste.component.css'
})
export class AjusteComponent {

}
