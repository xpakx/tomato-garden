import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Page } from 'src/app/entity/page';
import { PomodoroMin } from 'src/app/entity/pomodoro-min';
import { StatsService } from 'src/app/service/stats.service';

@Component({
  selector: 'app-timeline-modal',
  templateUrl: './timeline-modal.component.html',
  styleUrls: ['./timeline-modal.component.css']
})
export class TimelineModalComponent implements OnInit {
  @Output() closeEvent = new EventEmitter<boolean>();
  page: number = 0;
  totalPages: number = 0;
  pomodoros: PomodoroMin[] = [];

  constructor(private service: StatsService) { }

  ngOnInit(): void {
    this.service.getTimeline().subscribe(
      (response: Page<PomodoroMin>) => {
        this.page = response.number;
        this.totalPages = response.totalPages;
        this.pomodoros = response.content;
      },
      (error: HttpErrorResponse) => {}
    )
  }

  loadPage(page: number): void {
    if(!(page>=0 && page<this.totalPages)) {
      return;
    }
    this.service.getTimelineByPage(page).subscribe(
      (response: Page<PomodoroMin>) => {
        this.page = response.number;
        this.totalPages = response.totalPages;
        this.pomodoros = response.content;
      },
      (error: HttpErrorResponse) => {}
    )
  }

  close(): void {
    this.closeEvent.emit(true);
  }

  getTomato(pomodoro: PomodoroMin): string {
    if(pomodoro.failed) { return 'tomato0'; }
    if(pomodoro.succeed) { return 'tomato5'; }
    return 'tomato0'
  }
}
