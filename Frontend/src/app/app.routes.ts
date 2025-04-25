import { Routes } from '@angular/router';
import { AboutUsComponent } from './about-us/about-us.component';
import { HeaderComponent } from './header/header.component';
import { ContactComponent } from './contact/contact.component'; 
import { ProfileComponent }  from './profile/profile.component';
import { DiscoverComponent } from './discover/discover.component';
import { MessagesComponent } from './messages/messages.component';
import { AuthGuard } from './auth/auth.guard';
import { LoginRegComponent } from './login-reg/login-reg.component';

export const routes: Routes = [
  // öffentlich (nicht eingeloggt)
  { path: '',          component: HeaderComponent },
  { path: 'about-us',  component: AboutUsComponent },
  { path: 'contact',   component: ContactComponent },
  { path: 'login', component: LoginRegComponent },// kann vielleicht raus

  // geschützte Bereiche (nur eingeloggt)
  { path: 'profile',   component: ProfileComponent,  canActivate: [AuthGuard] },
  { path: 'discover',  component: DiscoverComponent , canActivate: [AuthGuard] },
  { path: 'messages',  component: MessagesComponent , canActivate: [AuthGuard] },

  // Fallback
  { path: '**', redirectTo: '' }
];