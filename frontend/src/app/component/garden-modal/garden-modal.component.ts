import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { StatsResponse } from 'src/app/entity/stats-response';
import { StatsService } from 'src/app/service/stats.service';

@Component({
  selector: 'app-garden-modal',
  templateUrl: './garden-modal.component.html',
  styleUrls: ['./garden-modal.component.css']
})
export class GardenModalComponent implements OnInit {
  @Output() closeEvent = new EventEmitter<boolean>();
  failed: number = 0;
  succeed: number = 0;

  constructor(private service: StatsService) { }

  ngOnInit(): void {
    this.service.getStats({tagId: undefined, after: undefined, before: undefined}).subscribe(
      (response: StatsResponse) => {
        this.failed = response.failed;
        this.succeed = response.succeed;
      },
      (error: HttpErrorResponse) => {}
    )
  }

  close(): void {
    this.closeEvent.emit(true);
  }
}
