import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StatsResponse } from 'src/app/entity/stats-response';
import { Tag } from 'src/app/entity/tag';
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
  form: FormGroup;

  constructor(private service: StatsService, private fb: FormBuilder) { 
    this.form = this.fb.group(
      {
        before: ['', []],
        after: ['', []]
      }
    );
  }

  ngOnInit(): void {
    this.service.getStats({tagId: undefined, after: undefined, before: undefined}).subscribe(
      (response: StatsResponse) => {
        this.failed = response.failed;
        this.succeed = response.succeed;
      },
      (error: HttpErrorResponse) => {}
    )
  }

  apply(): void {
    let afterDate = new Date(this.form.controls.after.value);
    let beforeDate = new Date(this.form.controls.before.value);
    this.service.getStats({
      tagId: this.tag ? this.tag.id : undefined, 
      after: afterDate, 
      before: beforeDate}).subscribe(
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
