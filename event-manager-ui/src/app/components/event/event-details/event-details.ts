import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { EventService } from '../../../services/event/event.service';
import { Router } from '@angular/router';
import { EventResponse } from '../../../models/event-response';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Participant } from '../../../models/participant';

@Component({
  selector: 'app-event-details',
  imports: [FormsModule,CommonModule],
  templateUrl: './event-details.html',
  styleUrl: './event-details.scss',
})
export class EventDetails implements OnInit{

  eventItem : EventResponse = {} as EventResponse
  participants : Array<Participant> = []
  isEditing : boolean = false
  showParticipants : boolean = false

  constructor(
    private eventService : EventService,
    private router : Router,
    private cdr: ChangeDetectorRef
  ){} 

  ngOnInit(): void {
    this.findEvent();
  }

  findEvent(){
    const id = this.router.currentNavigation()?.extras?.state?.['item'].id ?? history.state?.item.id ?? null;
    this.eventService.findEvent(
      id
    ).subscribe({
      next: (response) => {
        this.eventItem = response as EventResponse
        this.cdr.detectChanges();
      },
      error: (err) => {

      }
    })
  }

  cancel(){
    this.eventService.cancel(
      this.eventItem.id
    ).subscribe({
      next: (response) => {
        alert('You have successfully cancelled the event.')
        this.eventItem.status = 'CANCELLED'
        // this.eventItem = response as EventResponse
        this.cdr.detectChanges()
      },
      error: (err) => {
        alert('An error has occurred, try cancelling agian.')
      }
    })
  }

  edit(){
    this.isEditing = !this.isEditing
  }

  getParticipants(){
    this.showParticipants = !this.showParticipants;
    if (!this.showParticipants) {
      console.log(this.participants);
      return;
    }
    else {
      this.eventService.getParticipants(
        this.eventItem.participantIDs as Array<number>
      ).subscribe({
        next: (response) => {
          this.participants = response
          this.cdr.detectChanges();
        },
        error: (err) => {
        }
      })
    }
  }

  save(){
    this.eventService.updateEvent(
      this.eventItem
    ).subscribe({
      next : (response) => {
        this.isEditing = !this.isEditing
        this.cdr.detectChanges()
        // this.eventService.findEvent(
        //   response
        // ).subscribe({
        //     next: (response) => {
        //       this.eventItem = response as EventResponse
        //       this.cdr.detectChanges();
        //     }
        // })
      },
      error : (err) => {
        alert("An error has occured, try saving again.")
      }
    })
  }

  logout(){
    localStorage.removeItem('token')
  }
}
