import { Component } from '@angular/core';
import { EventRequest } from '../../../models/event-request';
import { EventService } from '../../../services/event/event.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EventResponse } from '../../../models/event-response';

@Component({
  selector: 'app-event.component',
  imports: [FormsModule, CommonModule],
  templateUrl: './event.component.html',
  styleUrl: './event.component.scss',
})
export class SaveEventComponent {

  event : EventRequest = {title: '', description: '',time: '', location: ''}
  files : Array<File> = []
  errors: Array<string> = [];

  constructor(
    private eventService: EventService, 
    private router : Router
  ){}

  saveEvent(){
    this.eventService.addEvent(
      this.event
    ).subscribe({
        next: (response) => {
          const formData = new FormData();
          this.files.forEach(file => {
            formData.append('files', file, file.name);
          });
          this.eventService.uploadfiles(
            response,
            formData
          ).subscribe({
            next: () => {
              this.router.navigate(['/events/upcoming'])
            }
          })
        },
        error: (err) => {
          this.errors = err.error.validationErrors
        }
      })
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.files.push(file);

      input.value = '';
    }
  }

  deleteFile(index: number): void {
    this.files.splice(index, 1);
  }

  resetFiles(): void {
    this.files = [];
  }
  
  logout(){
    localStorage.removeItem('token')
  }
}

