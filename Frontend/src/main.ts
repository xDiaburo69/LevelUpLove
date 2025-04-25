import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { routes } from '../src/app/app.routes'
import { AuthInterceptor } from './app/auth/auth.interceptors';


bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),         // ✅ Routing aktivieren
    ...(appConfig.providers || []),
    provideHttpClient(withInterceptorsFromDi()),{
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    provideAnimations(),

  ]

}).catch((err) => console.error(err));