import { Component } from '@angular/core';
import { UserRegistrationRequest } from '../../models/user-registration-request';
import { RegistrationService } from '../../services/registration/registration.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-registration.component',
  imports: [FormsModule, CommonModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.scss',
})
export class RegistrationComponent {

  registrationRequest: UserRegistrationRequest = {firstName: '',dateOfBirth: '', lastName: '', email: '', password: ''};
  errors: Array<string> = [];

    constructor(
    private router: Router,
    private registrationService: RegistrationService
  ){}

  register(){
    this.errors = []
    this.registrationService.register(
      this.registrationRequest
    ).subscribe({
      next: (response) => {
        this.router.navigate(['login'])
      },
      error: (err) => {
        console.log(this.errors);
        if(err.error.validationErrors){
          this.errors = err.error.validationErrors
        } else {
          this.errors.push(err.error.error)
        }
      }
    })
  }

  login(){
    this.router.navigate(['login'])
  }

}
