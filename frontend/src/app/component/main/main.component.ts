import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Subscription, interval } from 'rxjs';
import { PomodoroService } from 'src/app/service/pomodoro.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  public minutes: number = 25;
  public current: number = 0;
  private interval?: Subscription;
  started: boolean = false;
  @Output() menuEvent = new EventEmitter<boolean>();

  constructor(private pomodoroService: PomodoroService) { }

  start(): void {
    this.started = true;
    this.current = 0;
    this.interval = interval(1000).subscribe((x) => this.step());
  }

  step(): void {
    if(this.current >= this.minutes*60) {
      this.started = false;
      this.interval?.unsubscribe();
    } else {
      this.current += 1;
    }
  }

  cancel(): void {
    this.started = false;
    this.current = 0;
    this.interval?.unsubscribe();
  }

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

  get stage(): String {
    let fraction = this.fraction;
    if(fraction > 0.90) { return "tomato5"; }
    if(fraction > 0.75) { return "tomato4"; }
    if(fraction > 0.50) { return "tomato3"; }
    if(fraction > 0.25) { return "tomato2"; }
    if(fraction > 0.10) { return "tomato1"; }
    return "tomato0";
  }

  ngOnInit(): void {
  }

  switchMenu(): void {
    this.menuEvent.emit(true);
  }
}
