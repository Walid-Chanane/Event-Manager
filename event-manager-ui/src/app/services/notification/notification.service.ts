import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NotificationResponse } from '../../models/notification-response';
import { HttpClient } from '@angular/common/http';
import { NOTIFICATION_SERVICE_URL } from '../api-configuration';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  
  constructor(private http: HttpClient){}

  findNotifications(): Observable<NotificationResponse[]> {
    return this.http.get<NotificationResponse[]>(NOTIFICATION_SERVICE_URL + '/notif/get');
  }
  
}
