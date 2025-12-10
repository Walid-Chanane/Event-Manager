import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationRequest } from '../../models/authentication-request';
import { AuthenticationResponse } from '../../models/authentication-response';
import { USER_SERVICE_URL } from '../api-configuration';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private http: HttpClient) {}
  authenticate(request: AuthenticationRequest, context?: any): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(USER_SERVICE_URL + '/auth/authenticate', request, { context });
  }
}
