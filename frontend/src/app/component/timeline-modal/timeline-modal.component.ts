import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Page } from 'src/app/service/page';
import { PomodoroMin } from 'src/app/service/pomodoro-min';
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

  close(): void {
    this.closeEvent.emit(true);
  }
}
