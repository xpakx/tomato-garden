import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Settings } from '../entity/settings';
import { DefaultSettingsService } from './default-settings.service';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  defaultPomodoroLength: number = 25;
  defaultBreakLength: number = 5;
  defaultFocus: boolean = false;

  constructor(private service: DefaultSettingsService) { }

  load() {
    this.service.getSettings().subscribe(
      (response: Settings) => {
        this.saveSettings(response);
      },
      (error: HttpErrorResponse) => {
        
      }
    )
  }

  saveSettings(response: Settings) {
    this.defaultBreakLength = response.defaultBreakLength;
    this.defaultPomodoroLength = response.defaultPomodoroLength;
    this.defaultFocus = response.defaultFocus;
  }
}
