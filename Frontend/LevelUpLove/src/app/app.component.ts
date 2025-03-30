import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavigationComponent } from './navigation/navigation.component';
import { HeaderComponent } from './header/header.component';
import { GrettingstextComponent } from './grettingstext/grettingstext.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,HeaderComponent,NavigationComponent,GrettingstextComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'LevelUpLove';
}
