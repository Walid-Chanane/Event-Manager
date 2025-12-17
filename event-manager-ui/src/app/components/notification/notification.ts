import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NotificationResponse } from '../../models/notification-response';
import { Router } from '@angular/router';
import { NotificationService } from '../../services/notification/notification.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TokenService } from '../../services/token/token.service';

@Component({
  selector: 'app-notification',
  imports: [FormsModule, CommonModule],
  templateUrl: './notification.html',
  styleUrl: './notification.scss',
})
export class Notification implements OnInit{

  notifications : NotificationResponse[] = []
  
  constructor(
    private notificationService : NotificationService,
    private tokenService : TokenService,
    private cdr: ChangeDetectorRef
  ){} 

  ngOnInit(): void {
    this.findNotifications()
  }

  findNotifications(){
      this.notificationService.findNotifications()
        .subscribe({
          next: (response) => {
            this.notifications = response as NotificationResponse[]
            this.cdr.detectChanges();
          },
          error: (err) => {
    
          }
        })
  }

  isAdmin(): boolean {
    var authorities : Array<string> =  this.tokenService.extractAuthorities()
    return authorities.includes('ROLE_EMPLOYEE') || authorities.includes('ROLE_ADMIN')
  }

  logout(){
    localStorage.removeItem('token')
  }
}
