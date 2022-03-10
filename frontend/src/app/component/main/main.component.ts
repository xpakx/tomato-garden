import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Tag } from 'src/app/entity/tag';
import { PomodoroService } from 'src/app/service/pomodoro.service';
import { BreakTimer } from 'src/app/utils/break-timer';
import { PomodoroTimer } from 'src/app/utils/pomodoro-timer';
import { Timer } from 'src/app/utils/timer';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  public minutes: number = 1;
  pomodoroId?: number;
  @Output() menuEvent = new EventEmitter<boolean>();
  message: string = "";
  invalid: boolean = false;
  deepFocus: boolean = false;

  break: boolean = false;
  breakMinutes: number = 1;

  timer: Timer;

  constructor(private service: PomodoroService) {
    this.timer = this.newPomodoro();
  }

  start() {
    this.timer.start();
  }

  switchPause(): void {
    if(!this.timer.started) {
      this.start()
    }

    if(!this.break && this.deepFocus) {
      return;
    }

    if(this.timer.paused) {
      this.restart();
    } else {
      this.pause();
    }
  }

  pause(): void {
    this.timer.pause();
  }

  restart(): void {
    this.timer.restart();
  }

  cancel(): void {
    this.timer.cancel();
  }

  private newPomodoro(): PomodoroTimer {
    return new PomodoroTimer(this, this.service, this.minutes);
  }

  private newBreak(): BreakTimer {
    return this.timer = new BreakTimer(this, this.minutes);
  }

  skip(): void {
    this.timer = this.break ? this.newPomodoro() : this.newBreak();
    this.break = !this.break;
  }

  stop(): void {
    this.timer = this.break ? this.newBreak() : this.newPomodoro();
  }

  loadError(error: HttpErrorResponse): void {
    this.message = error.error.message;
    this.invalid = true;
  }
  
  get activeMinutes(): number {
    return this.break ? this.breakMinutes : this.timer.minutes;
  }

  get remaining(): number {
    return this.activeMinutes * 60 - this.timer.current;
  }

  get minutesLeft(): number {
    return Math.floor(this.remaining/60);
  }

  get secondsLeft(): number {
    return this.remaining - this.minutesLeft*60;
  }

  get fraction(): number {
    return this.timer.current/(this.activeMinutes * 60);
  }

  get dashStyle(): String {
    return ((this.remaining/(this.activeMinutes*60)) * 283).toFixed(0) + ' 283';
  }

  get stage(): String {
    let fraction = this.fraction;
    if(this.break || fraction > 0.90) { return "tomato5"; }
    if(fraction > 0.75) { return "tomato4"; }
    if(fraction > 0.50) { return "tomato3"; }
    if(fraction > 0.25) { return "tomato2"; }
    if(fraction > 0.10) { return "tomato1"; }
    return "tomato0";
  }

  get breakStarted(): boolean {
    return this.break && this.timer.started;
  }

  get breakNotStarted(): boolean {
    return this.break && !this.timer.started;
  }

  get pomodoroStarted(): boolean {
    return !this.break && this.timer.started;
  }

  get pomodoroNotStarted(): boolean {
    return !this.break && !this.timer.started;
  }

  ngOnInit(): void {
  }

  switchMenu(): void {
    this.menuEvent.emit(true);
  }

  showTag: boolean = false;
  tag: Tag | undefined;

  openTag() {
    this.showTag = true;
  }

  closeTag() {
    this.showTag = false;
  }

  changeTag(tag: Tag) {
    this.tag = tag;
    this.closeTag();
  }
}
