import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  defaultPomodoroLength: number = 25;
  defaultBreakLength: number = 5;
  defaultFocus: boolean = false;

  constructor() { }
}
