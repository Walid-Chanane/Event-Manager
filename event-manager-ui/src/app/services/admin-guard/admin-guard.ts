import { Injectable } from '@angular/core';
import { TokenService } from '../token/token.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard {
  constructor(
    private tokenService: TokenService,
    private router: Router
  ) {}

  canActivate(): boolean {
    var authorities : Array<string> =  this.tokenService.extractAuthorities()
    if (this.tokenService.isTokenNotValid() || (!authorities.includes('ROLE_EMPLOYEE') && !authorities.includes('ROLE_ADMIN'))) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
