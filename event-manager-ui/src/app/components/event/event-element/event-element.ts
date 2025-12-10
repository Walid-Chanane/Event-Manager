import { Component, Input, OnInit } from '@angular/core';
import { EventResponse } from '../../../models/event-response';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-event-element',
  imports: [FormsModule, CommonModule],
    standalone: true,
  templateUrl: './event-element.html',
  styleUrls: ['./event-element.scss'],
})
export class EventElement{

  private _event: EventResponse = {id: 0, title: '', description: '', time: new Date(), location: '', status: '', files: []};

  get eventItem(): EventResponse{
    return this._event;
  }

  @Input()
  set eventItem(value: EventResponse) {
    this._event = value
  }
}
