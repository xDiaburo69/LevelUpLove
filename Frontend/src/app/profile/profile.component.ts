import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpClient, HttpHeaders } from '@angular/common/http';
import { UserProfile } from './profile.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileId!: string;
  profile: UserProfile = {
    username: '',
    name: '',
    gender: '',
    hometown: '',
    age: 0,
    birthdate: new Date(),
    bio: '',
    music: '',
    height: 0,
    education: '',
    familyPlans: '',
    smoking: '',
    alcohol: '',
    pets: '',
    occupation: '',
    interests: '',
    typeOfLiving: ''
  };

  editMode = false;
  showModal = false;
  images: string[] = [
    '../../assets/images/himeji-castle-9500850_1280.jpg',
    '../../assets/images/kenroku-en-garden-9492642_1280.jpg',
    '../../assets/images/koko-en-garden-9492674_1280.jpg',
    '../../assets/images/prunus-9502226_1280.jpg'
  ];
  visibleImages = [...this.images];
  currentIndex = 0;

  constructor(
    private http: HttpClient,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const token = localStorage.getItem('authToken');
    if (!token) {
      console.error('No auth token found');
      return;
    }
    this.profileId = JSON.parse(atob(token.split('.')[1])).id;

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.http
      .get<UserProfile>('http://localhost:8080/api/users/me/profile', { headers })
      .subscribe({
        next: data => {
          this.profile = { ...this.profile, ...data };
          this.cd.detectChanges();
          console.log('✅ Profile loaded', this.profile);
        },
        error: err => console.error('❌ Error loading profile', err)
      });
  }

  toggleEdit(): void {
    this.editMode = !this.editMode;
  }

  saveProfile(): void {
    this.editMode = false;
    this.showModal = true;

    const token = localStorage.getItem('authToken')!;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.http
      .post<UserProfile>(
        'http://localhost:8080/api/users/me/profile',
        this.profile,
        { headers }
      )
      .subscribe({
        next: res => console.log('✅ Profile saved', res),
        error: err => console.error('❌ Error saving profile', err)
      });
  }

  closeModal(): void {
    this.showModal = false;
  }

  prevImage(): void {
    const last = this.visibleImages.pop();
    if (last) this.visibleImages.unshift(last);
  }

  nextImage(): void {
    const first = this.visibleImages.shift();
    if (first) this.visibleImages.push(first);
  }

  onImageUpload(event: any): void {
    const file = event.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = () => {
      this.visibleImages.push(reader.result as string);
      if (this.visibleImages.length > 5) this.visibleImages.shift();
      this.uploadImageToServer(file);
    };
    reader.readAsDataURL(file);
  }

  deleteImage(index: number): void {
    this.visibleImages.splice(index, 1);
  }

  private uploadImageToServer(file: File): void {
    const token = localStorage.getItem('authToken')!;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const formData = new FormData();
    formData.append('image', file);
    this.http
      .post(`http://localhost:8080/api/users/me/upload`, formData, { headers })
      .subscribe({
        next: res => console.log('✅ Image uploaded', res),
        error: err => console.error('❌ Error uploading image', err)
      });
  }
}