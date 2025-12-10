import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { RegistrationComponent } from './components/registration/registration.component';
import { SaveEventComponent } from './components/event/save-event/save-event.component';
import { UpcomingEvents } from './components/event/upcoming-events/upcoming-events';

export const routes: Routes = [
  {
    path: 'login',
    component: Login
  },
  {
    path: 'register',
    component: RegistrationComponent
  },
  {
    path: 'events/addEvent',
    component: SaveEventComponent
  },
  {
    path: 'events/upcoming',
    component: UpcomingEvents,
    runGuardsAndResolvers: 'always'
  }
];

