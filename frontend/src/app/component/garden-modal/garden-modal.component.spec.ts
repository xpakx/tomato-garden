import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GardenModalComponent } from './garden-modal.component';

describe('GardenModalComponent', () => {
  let component: GardenModalComponent;
  let fixture: ComponentFixture<GardenModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GardenModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GardenModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
