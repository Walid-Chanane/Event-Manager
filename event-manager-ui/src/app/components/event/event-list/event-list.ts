import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { EventService } from '../../../services/event/event.service';
import { Router } from '@angular/router';
import { PageUpcomingEvents } from '../../../models/page-upcoming-events';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EventElement } from '../event-element/event-element';

@Component({
  selector: 'app-event-list',
  imports: [FormsModule, CommonModule, EventElement],
  templateUrl: './event-list.html',
  styleUrl: './event-list.scss',
})
export class EventList implements OnInit{

  eventResponse: PageUpcomingEvents = {content : [], totalPages:1};

  page = 0
  size = 10
  direction = 'ASC'
  sortBy = 'id'
  message: string = ''
  success: boolean = true

  constructor(
    private eventService: EventService,
    private cdr: ChangeDetectorRef
  ){}

  ngOnInit(): void {
    this.findAllEvents()
  }

  private findAllEvents(){
    this.eventService.findAllEvents({
      page: this.page,
      size: this.size,
      direction: this.direction,
      sortBy: this.sortBy
    }).subscribe({
      next: (events) => {
        this.eventResponse = events
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error fetching events', err),
    });
  }

  onFilterChange(){
    this.findAllEvents() 
  }
  
  goToFirstPage(){
    this.page = 0
    this.findAllEvents()
  }

  goToPreviousPage(){
    this.page--
    this.findAllEvents()
  }

  goToPage(index: number){
    this.page = index
    this.findAllEvents()
  }

  goToNextPage(){
    this.page++
    this.findAllEvents()
  }

  goToLastPage(){
    this.page = this.eventResponse.totalPages as number -1
    this.findAllEvents()
  }

  get isLastPage(): boolean{
    return this.page == this.eventResponse.totalPages as number -1
  }

    logout(){
    localStorage.removeItem('token')
  }
}
