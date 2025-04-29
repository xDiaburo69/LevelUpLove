import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavigationComponent } from './navigation/navigation.component';
import { HeaderComponent } from './header/header.component';
import { GrettingstextComponent } from './grettingstext/grettingstext.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { DiscoverComponent } from './discover/discover.component';
import { ProfileComponent } from './profile/profile.component';
import { MessagesComponent } from './messages/messages.component';
import { MatchingComponent } from './matching/matching.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,HeaderComponent,NavigationComponent,GrettingstextComponent,AboutUsComponent,DiscoverComponent,ProfileComponent,MessagesComponent,MatchingComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'LevelUpLove';
}
