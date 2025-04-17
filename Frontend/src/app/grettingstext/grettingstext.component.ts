import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginRegComponent } from '../login-reg/login-reg.component';

@Component({
  selector: 'app-grettingstext', // Der Selector muss mit dem HTML-Tag übereinstimmen
  standalone: true,
  imports: [CommonModule, LoginRegComponent],
  templateUrl: './grettingstext.component.html',
  styleUrls: ['./grettingstext.component.css']
})
export class GrettingstextComponent {
  showMessage = false; // Steuert die Anzeige von "Los geht's!"
  progress = 0; // Fortschritt in Prozent
  private countdownInterval: any; // Speichert das Intervall
  private countdownTimeout: any; // Speichert den Timeout

  startCountdown() {
    this.progress = 0; // Setze den Fortschritt zurück
    this.countdownInterval = setInterval(() => {
      if (this.progress < 100) {
        this.progress += 2; // Erhöhe den Fortschritt
      }
    }, 100); // Aktualisiere alle 100ms

    this.countdownTimeout = setTimeout(() => {
      this.showMessage = true; // Zeige die Nachricht an
      clearInterval(this.countdownInterval); // Stoppe den Fortschrittsbalken
    }, 5000); // 5000 Millisekunden = 5 Sekunden
  }

  resetCountdown() {
    clearTimeout(this.countdownTimeout); // Stoppe den Countdown
    clearInterval(this.countdownInterval); // Stoppe den Fortschrittsbalken
    this.progress = 0; // Setze den Fortschritt zurück
    this.showMessage = false; // Verstecke die Nachricht
  }
}