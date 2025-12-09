import { Injectable } from '@angular/core';
import { UserRegistrationRequest } from '../../models/user-registration-request';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { USER_SERVICE_URL } from '../api-configuration';

@Injectable({
  providedIn: 'root',
})
export class RegistrationService {

  constructor(private http: HttpClient) {}
  register(request: UserRegistrationRequest, context?: any): Observable<void> {
    return this.http.post<void>(USER_SERVICE_URL + '/auth/register', request, { context });
  }
}
