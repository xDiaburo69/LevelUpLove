import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {
  name: string = '';
  email: string = '';
  message: string = '';
  showSuccessMessage: boolean = false;
  errorMessage: string = '';

  onSubmit(): void {
    if (this.name && this.email && this.message) {
      this.showSuccessMessage = true;
      this.errorMessage = '';
      this.resetForm();
    } else {
      this.showSuccessMessage = false;
      this.errorMessage = '⚠️ Bitte fülle alle Felder aus, bevor du die Nachricht sendest!';
    }
  }

  resetForm(): void {
    this.name = '';
    this.email = '';
    this.message = '';
  }
}