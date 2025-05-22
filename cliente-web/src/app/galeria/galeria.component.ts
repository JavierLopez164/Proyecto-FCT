import { Component,OnInit } from '@angular/core';
import { HeaderWashabiComponent } from '../header-washabi/header-washabi.component';
import { FooterComponent } from '../footer/footer.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-galeria',
  imports: [CommonModule ,HeaderWashabiComponent,FooterComponent,  MatFormFieldModule,MatSelectModule,MatOptionModule, MatCardModule],
  templateUrl: './galeria.component.html',
  styleUrl: './galeria.component.css'
})
export class GaleriaComponent implements OnInit {
 foodItems = [
    { id: 1, name: 'Pizza Margherita', imageUrl: 'img/anime.jpeg', restaurant: 'Pizzeria Roma', dateAdded: new Date('2023-01-15') },
    { id: 2, name: 'Sushi', imageUrl: 'img/anime.jpeg', restaurant: 'Sushi Bar', dateAdded: new Date('2023-05-05') },
    { id: 3, name: 'Tacos', imageUrl: 'img/anime.jpeg', restaurant: 'Taquería El Camino', dateAdded: new Date('2023-04-01') },
    { id: 4, name: 'Burger', imageUrl: 'img/anime.jpeg', restaurant: 'Burger King', dateAdded: new Date('2023-03-25') }
  ];

  filteredFoodItems: any[] = [];
  restaurants: string[] = [];
  selectedRestaurant: string = 'all';
  sortBy: string = 'name';

  ngOnInit(): void {
    // Extrae restaurantes únicos
    this.restaurants = Array.from(new Set(this.foodItems.map(item => item.restaurant)));

    // Inicializa el listado con los filtros por defecto
    this.applyFilters();
  }

  // Aplica los filtros combinados
  applyFilters(): void {
    let result = [...this.foodItems];

    // Filtro por restaurante
    if (this.selectedRestaurant !== 'all') {
      result = result.filter(item => item.restaurant === this.selectedRestaurant);
    }

    // Ordenamiento
    switch (this.sortBy) {
      case 'dateNewest':
        result.sort((a, b) => b.dateAdded.getTime() - a.dateAdded.getTime());
        break;
      case 'dateOldest':
        result.sort((a, b) => a.dateAdded.getTime() - b.dateAdded.getTime());
        break;
      default:
        result.sort((a, b) => a.name.localeCompare(b.name));
        break;
    }

    this.filteredFoodItems = result;
  }

  // Cambia ordenamiento
  onSortChange(event: any): void {
    this.sortBy = event.value;
    this.applyFilters();
  }

  // Cambia restaurante
  onRestaurantFilterChange(event: any): void {
    this.selectedRestaurant = event.value;
    this.applyFilters();
  }
}
