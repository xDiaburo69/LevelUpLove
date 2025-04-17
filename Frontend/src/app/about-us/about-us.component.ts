import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-about-us',
  standalone: true,
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.css'],
  imports: [CommonModule, RouterModule],
})
export class AboutUsComponent {
  faqs = [
    { question: 'Was ist LevelUpLove?', answer: 'Ein Ort für Nerds, Geeks und fantastische Romanzen.' },
    { question: 'Wer kann mitmachen?', answer: 'Alle, die ihre Nerdigkeit feiern wollen!' },
    { question: 'Welche Fandoms sind vertreten?', answer: 'Von Star Wars bis Witcher, alles ist willkommen.' }
  ];
}
