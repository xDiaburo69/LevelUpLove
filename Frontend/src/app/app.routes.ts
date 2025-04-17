import { Routes } from '@angular/router';
import { AboutUsComponent } from './about-us/about-us.component';
import { HeaderComponent } from './header/header.component';
import { ContactComponent } from './contact/contact.component'; // Importiere die neue Komponente

export const routes: Routes = [
  { path: '', component: HeaderComponent },
  { path: 'about-us', component: AboutUsComponent },
  {path: 'contact', component: ContactComponent}, // Füge den neuen Pfad hier hinzu
];
