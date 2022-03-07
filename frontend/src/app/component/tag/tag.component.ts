import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Tag } from 'src/app/entity/tag';
import { TagService } from 'src/app/service/tag.service';

@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.css']
})
export class TagComponent implements OnInit {
  public invalid: boolean = false;
  public message: string = '';
  @Output() closeEvent = new EventEmitter<boolean>();
  form: FormGroup;
  tags: Tag[] = [];

  constructor(private fb: FormBuilder, private service: TagService) { 
    this.form = this.fb.group({
      name: ['', Validators.required],
      color: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.service.getAll().subscribe(
      (response: Tag[]) => {
        this.tags = response
      },
      (error: HttpErrorResponse) => {
        this.message = error.error.message;
        this.invalid = true;
      }
    );
  }

  add() {
    this.service.add({name: this.form.controls.name.value, color: this.form.controls.color.value}).subscribe(
      (response: Tag) => {
        this.tags.push(response);
      },
      (error: HttpErrorResponse) => {
        this.message = error.error.message;
        this.invalid = true;
      }
    );
  }

  close() {
    this.closeEvent.emit(false);
  }
}
