import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { RegistrationComponent } from './components/registration/registration.component';

export const routes: Routes = [
  {
    path: 'login',
    component: Login
  },
  {
    path: 'register',
    component: RegistrationComponent
  }
];

