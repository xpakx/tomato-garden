import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Settings } from 'src/app/entity/settings';
import { DefaultSettingsService } from 'src/app/service/default-settings.service';
import { SettingsService } from 'src/app/service/settings.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  form: FormGroup;
  focus: boolean;
  @Output() closeEvent = new EventEmitter<boolean>();

  constructor(private fb: FormBuilder, private settings: SettingsService, private service: DefaultSettingsService) { 
    this.form = this.fb.group({
      pomodoro: [this.settings.defaultPomodoroLength, Validators.required],
      break: [this.settings.defaultBreakLength, Validators.required]
    });
    this.focus = this.settings.defaultFocus;
  }

  ngOnInit(): void {
  }

  close() {
    this.closeEvent.emit(false);
  }

  switchFocus(): void {
    this.focus = !this.focus;
  }

  ok() {
    if(this.form.invalid) {
      return;
    }
    if(this.form.valid) {
      this.service.changeSettings({
        pomodoroLength: this.form.controls.pomodoro.value,
        breakLength: this.form.controls.break.value,
        focus: this.focus
      }).subscribe(
        (response: Settings) => {
          this.settings.saveSettings(response);
        },
        (error: HttpErrorResponse) => {}
      )
    }
    this.close();
  }
}
