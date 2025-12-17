import { ChangeDetectorRef, Component } from '@angular/core';
import { PageUpcomingEvents } from '../../../models/page-upcoming-events';
import { EventService } from '../../../services/event/event.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EventElement } from '../event-element/event-element';
import { TokenService } from '../../../services/token/token.service';

@Component({
  selector: 'app-upcoming-events',
  imports: [FormsModule, CommonModule, EventElement],
  templateUrl: './upcoming-events.html',
  styleUrl: './upcoming-events.scss',
})
export class UpcomingEvents {
  eventResponse: PageUpcomingEvents = {content : [], totalPages:1};

  page = 0
  size = 10
  direction = 'ASC'
  sortBy = 'title'
  message: string = ''
  success: boolean = true

  constructor(private eventService: EventService,private tokenService: TokenService, private router: Router, private cdr: ChangeDetectorRef){}
  
  ngOnInit(): void {
    this.findUpcomingEvents()
  }

  private findUpcomingEvents(){
    this.eventService.findUpcomingEvents({
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

  isAdmin(): boolean {
    var authorities : Array<string> =  this.tokenService.extractAuthorities()
    return authorities.includes('ROLE_EMPLOYEE') || authorities.includes('ROLE_ADMIN')
  }

  onFilterChange(){
    this.findUpcomingEvents() 
  }

  goToFirstPage(){
    this.page = 0
    this.findUpcomingEvents()
  }

  goToPreviousPage(){
    this.page--
    this.findUpcomingEvents()
  }

  goToPage(index: number){
    this.page = index
    this.findUpcomingEvents()
  }

  goToNextPage(){
    this.page++
    this.findUpcomingEvents()
  }

  goToLastPage(){
    this.page = this.eventResponse.totalPages as number -1
    this.findUpcomingEvents()
  }


  get isLastPage(): boolean{
    return this.page == this.eventResponse.totalPages as number -1
  }

  logout(){
    localStorage.removeItem('token')
  }
}
