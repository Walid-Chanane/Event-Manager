import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EventRequest } from '../../models/event-request';
import { EVENT_SERVICE_URL } from '../api-configuration';
import { Observable } from 'rxjs';
import { EventResponse } from '../../models/event-response';
import { PageUpcomingEvents } from '../../models/page-upcoming-events';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  constructor(private http: HttpClient) {}
  
  saveEvent(request: EventRequest, context?: any): Observable<void> {
    return this.http.post<void>(EVENT_SERVICE_URL + '/admin/save', request, { context });
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

}
