import { Component } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { HeaderWashabiComponent } from "../header-washabi/header-washabi.component";

@Component({
  selector: 'app-menu',
  imports: [FooterComponent, HeaderWashabiComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {

}
