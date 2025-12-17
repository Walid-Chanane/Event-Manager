import { Component, Input, OnInit } from '@angular/core';
import { EventResponse } from '../../../models/event-response';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TokenService } from '../../../services/token/token.service';
import { Router } from '@angular/router';
import { EventService } from '../../../services/event/event.service';

@Component({
  selector: 'app-event-element',
  imports: [FormsModule, CommonModule],
    standalone: true,
  templateUrl: './event-element.html',
  styleUrls: ['./event-element.scss'],
})
export class EventElement{

  private _event: EventResponse = {id: 0, title: '', description: '', time: '', location: '', status: '', files: []};

  constructor(
    private eventService: EventService,
    private tokenService: TokenService,
    private router: Router
  ){}

  get eventItem(): EventResponse{
    return this._event;
  }

  @Input()
  set eventItem(value: EventResponse) {
    this._event = value
  }

  isAdmin(): boolean {
    var authorities : Array<string> =  this.tokenService.extractAuthorities()
    return authorities.includes('ROLE_EMPLOYEE') || authorities.includes('ROLE_ADMIN')
  }

  goToDetails(){
    this.router.navigate(['event-details'], { state: { item: this.eventItem } });
  }

  register(){
    this.eventService.registerForEvent(
      this.eventItem.id
    ).subscribe({
      next: (response) => {
        alert('You have been successfully registrated.')
        //this.router.navigate(['/events/upcoming'])
      },
      error: (err) => {
        alert('An error has occurred, try registering agian.')
      }
    })
  }

  withdraw(){
    this.eventService.withdrawFromEvent(
      this.eventItem.id
    ).subscribe({
      next: (response) => {
        alert('You have successfully withdrew froom the event.')
      //  this.router.navigate(['/events/upcoming'])
      },
      error: (err) => {
        alert('An error has occurred, try withdrawing agian.')
      }
    })
  }
}
