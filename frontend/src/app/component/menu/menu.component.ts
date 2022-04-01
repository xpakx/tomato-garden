import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  @Output() settingsEvent = new EventEmitter<boolean>();
  @Output() timelineEvent = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit(): void {
  }

  openSettings(): void {
    this.settingsEvent.emit(true);
  }

  openTimeline(): void {
    this.timelineEvent.emit(true);
  }
}
