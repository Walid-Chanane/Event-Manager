import { Component } from '@angular/core';
import { AuthenticationRequest } from '../../models/authentication-request';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication/authentication.service';
import { TokenService } from '../../services/token/token.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {


  authRequest: AuthenticationRequest = {email: '', password: ''};
  errors: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) {}

  login() {
    this.errors = [];
    this.authService.authenticate(
      this.authRequest
    ).subscribe({
      next: (response) => {
        this.tokenService.token = response.token as string
        var authorities : Array<string> =  this.tokenService.extractAuthorities()
        if (authorities.includes('ROLE_ADMIN') || authorities.includes('ROLE_EMPLOYEE'))
        {this.router.navigate(['admin/events'])}
        else
         this.router.navigate(['/events/upcoming'])
      },
      error: (err) => {
        if(err.error.validationErrors){
          this.errors = err.error.validationErrors
        } else {
          this.errors.push(err.error)
        }
      }
    });
  }

}
