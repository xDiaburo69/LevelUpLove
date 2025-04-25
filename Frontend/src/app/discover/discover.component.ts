import {
  Component,
  AfterViewInit,
  ChangeDetectorRef
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-discover',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.css']
})
export class DiscoverComponent implements AfterViewInit {
  quizResult = '';

  constructor(private cd: ChangeDetectorRef) {}

  ngAfterViewInit(): void {
    // Features einfach anzeigen – ohne IntersectionObserver
    const features = document.querySelectorAll<HTMLElement>('.feature');
    features.forEach(f => {
      f.classList.remove('invisible');
      f.classList.add('visible');
    });
  
    const chatBox = document.getElementById('chat-box');
    if (chatBox) {
      chatBox.innerHTML = `
        <p><strong>🧙‍♂️ GandalfFan420:</strong> Du sollst nicht allein swipen!</p>
        <p><strong>👩‍🚀 SpaceBae88:</strong> Ich liebe dich bis zum Pluto und zurück 💫</p>
        <p><strong>🕹️ ZeldaZocker:</strong> Herzcontainer gefunden – bereit fürs nächste Level?</p>
      `;
    }
  }


  submitQuiz(): void {
    const answers: Record<string, number> = {
      Gryffindor: 0, Ravenclaw: 0, Hufflepuff: 0, Slytherin: 0,
      Hobbit: 0, Elf: 0, Zwerg: 0, Mensch: 0
    };
    const checked = document.querySelectorAll<HTMLInputElement>(
      'input[type="radio"]:checked'
    );
    if (checked.length < 4) {
      this.quizResult = 'Bitte beantworte alle Fragen!';
      this.cd.detectChanges();
      return;
    }
    checked.forEach(inp => {
      answers[inp.value] = (answers[inp.value] || 0) + 1;
    });

    const hogwarts = ['Gryffindor', 'Ravenclaw', 'Hufflepuff', 'Slytherin']
      .reduce((a, b) => answers[a] > answers[b] ? a : b);
    const lotr = ['Hobbit', 'Elf', 'Zwerg', 'Mensch']
      .reduce((a, b) => answers[a] > answers[b] ? a : b);

    this.quizResult = `
      🧙‍♂️ Du bist im Haus <strong>${hogwarts}</strong>!<br>
      🪐 Und du gehörst zur Spezies <strong>${lotr}</strong>!
    `;
    this.cd.detectChanges();
  }
}