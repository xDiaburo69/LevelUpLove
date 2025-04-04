import { Routes } from '@angular/router';
import { HeaderComponent } from './header/header.component';
import { AboutUsComponent } from './about-us/about-us.component';

export const routes: Routes = [
    {path:"", component:HeaderComponent},
    {path:"about-us", component:AboutUsComponent},
];
