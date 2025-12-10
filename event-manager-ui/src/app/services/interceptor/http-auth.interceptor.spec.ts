import { TestBed } from '@angular/core/testing';

import { HttpAuthInterceptor } from './http-auth.interceptor';

describe('HttpAuthInterceptor', () => {
  let service: HttpAuthInterceptor;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpAuthInterceptor);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
