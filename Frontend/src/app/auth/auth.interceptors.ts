import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service'; // ggf. Pfad anpassen

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();

    // ➤ Keine Tokens für Login/Registration-Routen anhängen:
    if (req.url.includes('/login') || req.url.includes('/register')) {
      return next.handle(req);
    }

    // ➤ Wenn kein Token vorhanden ist, normal weiter
    if (!token) {
      return next.handle(req);
    }

    // ➤ Token anhängen
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    return next.handle(cloned);
  }
}
