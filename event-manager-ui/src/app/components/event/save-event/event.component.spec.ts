import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveEventComponent } from './save-event.component';

describe('EventComponent', () => {
  let component: SaveEventComponent;
  let fixture: ComponentFixture<SaveEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaveEventComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaveEventComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
