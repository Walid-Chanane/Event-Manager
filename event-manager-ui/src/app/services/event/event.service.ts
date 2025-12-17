import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EventRequest } from '../../models/event-request';
import { EVENT_SERVICE_URL, USER_SERVICE_URL } from '../api-configuration';
import { Observable } from 'rxjs';
import { EventResponse } from '../../models/event-response';
import { PageUpcomingEvents } from '../../models/page-upcoming-events';
import { Participant } from '../../models/participant';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  findAllEvents(page: {page: number, size: number, direction: string, sortBy: string}): Observable<PageUpcomingEvents> {
    return this.http.get<PageUpcomingEvents>(EVENT_SERVICE_URL + '/admin/get-events',
      {
      params: {
        page: page.page?.toString(),
        size: page.size?.toString(),
        direction: page.direction?.toString(),
        sortBy: page.sortBy?.toString()
      }
    }  
    );
  }
  findEvent(eventId: number): Observable<EventResponse> {
    return this.http.get<EventResponse>(EVENT_SERVICE_URL + '/admin/get-event/' + eventId);
  }
  getParticipants(participants: Array<number>): Observable<Participant[]> {
    return this.http.post<Participant[]>(USER_SERVICE_URL + '/admin/users/by-id', participants);
  }
  constructor(private http: HttpClient) {}
  
  addEvent(request: EventRequest, context?: any): Observable<number> {
    return this.http.post<number>(EVENT_SERVICE_URL + '/admin/add', request, { context });
  }
  
  updateEvent(request: EventResponse, context?: any): Observable<number> {
    return this.http.put<number>(EVENT_SERVICE_URL + '/admin/update', request, { context });
  }
  
  uploadfiles(eventId: any, files: FormData, context?: any): Observable<EventResponse> {
    return this.http.post<EventResponse>(EVENT_SERVICE_URL + '/admin/upload/' + eventId, files, { context });
  }

  findUpcomingEvents(page: {page: number, size: number, direction: string, sortBy: string}): Observable<PageUpcomingEvents> {
    return this.http.get<PageUpcomingEvents>(EVENT_SERVICE_URL + '/events/upcoming',
      {
      params: {
        page: page.page?.toString(),
        size: page.size?.toString(),
        direction: page.direction?.toString(),
        sortBy: page.sortBy?.toString()
      }
    }  
    );
  }

  registerForEvent(eventId: number): Observable<{}>{
    return this.http.patch<{}>(EVENT_SERVICE_URL + '/' + eventId + '/register', {})
  }

  withdrawFromEvent(eventId: number): Observable<{}>{
    return this.http.patch<{}>(EVENT_SERVICE_URL + '/' + eventId + '/withdraw', {})
  }

  cancel(eventId: number): Observable<EventResponse>{
    return this.http.patch<EventResponse>(EVENT_SERVICE_URL + '/admin/cancel/' + eventId, {})
  }

}
