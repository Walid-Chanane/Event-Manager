import { Injectable } from '@angular/core';
import { TokenService } from '../token/token.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class Guard {
  constructor(
    private tokenService: TokenService,
    private router: Router
  ) {}

  canActivate(): boolean {
    if (this.tokenService.isTokenNotValid()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
