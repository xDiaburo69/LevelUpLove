import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private _isLoggedIn = signal(false);
  private _username = signal<string | null>(null);
  private tokenKey = 'authToken';

 
  private loginUrl = 'http://localhost:8080/api/auth/login';
  private registerUrl = 'http://localhost:8080/api/auth/register';
  private profileUrl = 'http://localhost:8080/api/users/{id}/profile';

  constructor(private http: HttpClient) {
    // Token beim Start prüfen
    const token = localStorage.getItem(this.tokenKey);
    if (token) {
      this._isLoggedIn.set(true);
      // Optional: username vom Backend holen
    }
  }
  get isLoggedIn(): boolean {
    return this._isLoggedIn();
  }
  get username(): string | null {
    return this._username();
  }
  /** Login mit Token-Speicherung */
  login(email: string, password: string): Observable<any> {
    return this.http.post<{ username: string; token: string }>(`${this.loginUrl}`, { email, password }).pipe(
      tap(response => {
        this._isLoggedIn.set(true);
        this._username.set(response.username);
        localStorage.setItem(this.tokenKey, response.token);
      })
    );
  }

  /** Registrierung (wie vorher) */
  register(username: string, email: string, gender: string, password: string, birthdate: string): Observable<any> {
    return this.http.post(`${this.registerUrl}`, {
      username, email, gender, password, birthdate
    });
  }

  /** Logout: Token löschen */
  logout(): void {
    this._isLoggedIn.set(false);
    this._username.set(null);
    localStorage.removeItem(this.tokenKey);
  }

  /** Benutzerprofil laden */
  loadUserProfile(): Observable<any> {
    return this.http.get(`${this.profileUrl}`);
  }

  /** Token auslesen (z. B. für Interceptor) */
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }
}