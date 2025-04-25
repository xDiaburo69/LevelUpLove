import {
  Component,
  AfterViewInit,
  ChangeDetectorRef
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements AfterViewInit {
  images: string[] = [
    '../../assets/images/himeji-castle-9500850_1280.jpg',
    '../../assets/images/kenroku-en-garden-9492642_1280.jpg',
    '../../assets/images/koko-en-garden-9492674_1280.jpg',
    '../../assets/images/prunus-9502226_1280.jpg',
    // max. 5 Bilder möglich
  ];

  visibleImages = [...this.images];
  currentIndex = 0;

  profile = {
    username: 'Abenteurer123',
    gender: 'goblin',
    location: 'Berlin',
    age: 28,
    bio: 'Liebe Abenteuer und Magie!',
    music: 'Rock, Klassik',
    personality: 'Ex',
    height: '1,80m',
    education: 'Master in Informatik',
    kids: 'Keine',
    smoker: 'Nein',
    job: 'Softwareentwickler',
    livingSituation: 'alone'
  };

  editMode = false;
  showModal = false;

  constructor(
    private cd: ChangeDetectorRef,
    private http: HttpClient
  ) {}

  ngAfterViewInit(): void {
    // z. B. Animation triggern
  }

  toggleEdit(): void {
    this.editMode = !this.editMode;
  }

  saveProfile(): void {
    this.editMode = false;
    this.showModal = true;

    this.saveProfileToServer();
  }

  closeModal(): void {
    this.showModal = false;
  }

  prevImage(): void {
    const last = this.visibleImages.pop();
    if (last) {
      this.visibleImages.unshift(last);
    }
  }

  nextImage(): void {
    const first = this.visibleImages.shift();
    if (first) {
      this.visibleImages.push(first);
    }
  }

  onImageUpload(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        // Anzeige aktualisieren
        this.visibleImages.push(reader.result as string);

        // Nur 5 Bilder behalten
        if (this.visibleImages.length > 5) {
          this.visibleImages.shift();
        }

        // Bild an Backend schicken
        this.uploadImageToServer(file);
      };
      reader.readAsDataURL(file);
    }
  }

  deleteImage(index: number): void {
    this.visibleImages.splice(index, 1);
  }

  uploadImageToServer(file: File): void {
    const formData = new FormData();
    formData.append('image', file);

    this.http.post('https://dein-backend-url.de/api/upload', formData)
      .subscribe({
        next: res => console.log('✅ Upload erfolgreich:', res),
        error: err => console.error('❌ Fehler beim Upload:', err)
      });
  }

  
  saveProfileToServer(): void {
    this.http.post('https://dein-backend-url.de/api/profile', this.profile)
      .subscribe({
        next: res => console.log('✅ Profil erfolgreich gespeichert:', res),
        error: err => console.error('❌ Fehler beim Speichern:', err)
      });
  }
}