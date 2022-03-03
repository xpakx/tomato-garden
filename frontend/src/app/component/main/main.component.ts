import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  public minutes: number = 120;
  public current: number = 30*60;
  @Output() menuEvent = new EventEmitter<boolean>();

  constructor() { }

  get remaining(): number {
    return this.minutes * 60 - this.current;
  }

  get minutesLeft(): number {
    return Math.floor(this.remaining/60);
  }

  get secondsLeft(): number {
    return this.remaining - this.minutesLeft*60;
  }

  get fraction(): number {
    return this.current/(this.minutes * 60);
  }

  get dashStyle(): String {
    return ((this.remaining/(this.minutes*60)) * 283).toFixed(0) + ' 283';
  }

  ngOnInit(): void {
  }

  switchMenu(): void {
    this.menuEvent.emit(true);
  }

}
