// about-us.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

interface Faq {
  question: string;
  answer: string;
  expanded: boolean;
}

@Component({
  standalone: true,
  selector: 'app-about-us',
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.css'],
  imports: [CommonModule, RouterModule],
})
export class AboutUsComponent {
  faqs: Faq[] = [
    {
      question: 'Was ist LevelUpLove?',
      answer: 'Ein Ort für Nerds, Geeks und fantastische Romanzen.',
      expanded: false
    },
    {
      question: 'Wer kann mitmachen?',
      answer: 'Alle, die ihre Nerdigkeit feiern wollen!',
      expanded: false
    },
    {
      question: 'Welche Fandoms sind vertreten?',
      answer: 'Von Star Wars bis Witcher, alles ist willkommen.',
      expanded: false

    },
    {
      question: 'Was ist unsere Mission und Vision',
      answer: 'Wir wollen Nerds helfen ihren Schatz zu finden und das auf eine faire Weise.\n' +
              'Bei uns gibt es kein "schreibe 2 Nachrichten und kaufe dann Credits um weiter schreiben zu können".\n' +
              'Wir wollen Nerds untereinander verbinden, ohne an das große Geld zu denken.',
      expanded: false
    },
    {
      question: 'Was unterscheidet Uns von der Konkurrenz?',
      answer: 'Die meisten in der Dating Branche einfach nur das Geld sehen, welches sie mit Menschen machen können, die alleine leben.\n'+
              'Wir hingegen wollen, dass die Menschen sich verbinden, ohne Angst zu haben, dass die nächste Nachricht die letzte des Tages sein könnte.',
      expanded: false
    },
  ];

  toggleFaq(index: number) {
    this.faqs[index].expanded = !this.faqs[index].expanded;
  }
}
