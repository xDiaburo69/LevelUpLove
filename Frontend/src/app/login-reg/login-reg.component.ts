import { Component} from '@angular/core';
import { AuthService } from '../auth/auth.service'; // Pfad anpassen
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'; // Importiere HttpClientModule
import { OnInit } from '@angular/core';
import {
  trigger,
  style,
  animate,
  transition
} from '@angular/animations';




@Component({
  selector: 'app-login-reg',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login-reg.component.html',
  styleUrls: ['./login-reg.component.css'],
  animations: [
    trigger('slideFadeHorizontal', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateX(50%)' }),
        animate('600ms ease-out', style({ opacity: 1, transform: 'translateX(0)' }))
      ]),
      transition(':leave', [
        animate('600ms ease-in', style({ opacity: 0, transform: 'translateX(-50%)' }))
      ])
    ])
  ]
})

export class LoginRegComponent implements OnInit {
  isLoginMode = true;
  email = '';
  password = '';
  username = '';
  phone = '';
  birthdate = '';


  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    if (this.auth.isLoggedIn) {
      this.router.navigate(['/discover']);
    }
  }

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
  }

  doLogin() {
    this.auth.login(this.email, this.password).subscribe(
      response => {
        this.router.navigate(['/discover']);
      },
      error => {
        alert('Login fehlgeschlagen: ' + error.message);
      }
    );
  }

  doRegister() {
    this.auth.register(this.username, this.email, this.phone, this.password).subscribe(
      response => {
        alert('Registrierung erfolgreich! Bitte einloggen.');
        this.isLoginMode = true;
      },
      error => {
        alert('Registrierung fehlgeschlagen: ' + error.message);
      }
    );
  }
}