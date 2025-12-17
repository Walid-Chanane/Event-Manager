import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { RegistrationComponent } from './components/registration/registration.component';
import { SaveEventComponent } from './components/event/save-event/save-event.component';
import { UpcomingEvents } from './components/event/upcoming-events/upcoming-events';
import { EventDetails } from './components/event/event-details/event-details';
import { Guard } from './services/guard/guard';
import { EventList } from './components/event/event-list/event-list';
import { Notification } from './components/notification/notification';
import { AdminGuard } from './services/admin-guard/admin-guard';

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
    path: 'addEvent',
    component: SaveEventComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'events/upcoming',
    component: UpcomingEvents,
    canActivate: [Guard]
  },
  {
    path: 'admin/events',
    component: EventList,
    canActivate: [AdminGuard]
  },
  {
    path: 'event-details',
    component: EventDetails,
    canActivate: [AdminGuard]
  },
  {
    path: 'notifications',
    component: Notification,
    canActivate: [Guard]
  }
];

