import { Component, HostListener } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../auth/auth.service';  // Pfad anpassen
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent {
 
  isScrolled = false;

  constructor(
    public auth: AuthService,   // public, damit du es im Template nutzen kannst
    private router: Router
  ) {}

  @HostListener('window:scroll')
  onWindowScroll() {
    this.isScrolled = window.pageYOffset > 50;
  }

  get isLoggedIn() {
    return this.auth.isLoggedIn;
  }
  
  get username(): string | null {
    return this.auth.username;
  }
  
  logout() {
    this.auth.logout();
    this.router.navigate(['/']);
  }

}