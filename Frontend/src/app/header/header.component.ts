import { Component } from '@angular/core';
import { GrettingstextComponent } from '../grettingstext/grettingstext.component'; // Passe den Pfad an, falls nötig
import { CommonModule } from '@angular/common'
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  standalone: true, // Markiert die Komponente als Standalone
  imports: [GrettingstextComponent, CommonModule, RouterModule] // Importiere die GrettingstextComponent hier
})
export class HeaderComponent {
  isScrolled = false; // Beispiel für eine Variable
}