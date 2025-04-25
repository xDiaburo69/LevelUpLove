import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { routes } from '../src/app/app.routes'


bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),         // ✅ Routing aktivieren
    ...(appConfig.providers || []),
    provideHttpClient(),
    provideAnimations()
  ]

}).catch((err) => console.error(err));