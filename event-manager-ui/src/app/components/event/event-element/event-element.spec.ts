import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventElement } from './event-element';

describe('EventElement', () => {
  let component: EventElement;
  let fixture: ComponentFixture<EventElement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventElement]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventElement);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
