import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private _isLoggedIn = signal(false);
  private _username = signal<string | null>(null);


  // Getter für den Login-Status
  get isLoggedIn(): boolean {
    return this._isLoggedIn();
  }
  // Getter für den Benutzernamen
  get username(): string | null {
    return this._username();
  }

  private apiUrl = 'https://your-api-url.com'; // Backend-URL (oder Dummy)

  constructor(private http: HttpClient) {}

  // Dummy Login (Backend später!)
  login(email: string, password: string): Observable<any> {
    if (email === 'test@test.com' && password === '123456') {
      this._isLoggedIn.set(true);
      this._username.set('Madame Parker'); // ← hier später response.username
      return of({ message: 'Login erfolgreich', username: 'Madame Parker' });
    }

    this._isLoggedIn.set(false);
    this._username.set(null);
    return throwError(() => new Error('Falsche Zugangsdaten'));
  }

    /** ✅ Dummy Registrierung */
    register(username: string, email: string, phone: string, password: string): Observable<any> {
      // Später: Hier deine echte API!
      return of({ message: 'Registrierung erfolgreich', username });
    }

  logout(): void {
    this._isLoggedIn.set(false);
    this._username.set(null);
  }

  // Später: Benutzerprofil laden
  loadUserProfile(): Observable<any> {
    return this.http.get('/api/profile'); // später vom echten Backend
  }
}