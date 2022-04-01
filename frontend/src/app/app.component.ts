import { Component } from '@angular/core';
import { SettingsService } from './service/settings.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'tomato-garden';
  hideMenu: boolean = true;
  showLogin: boolean = true;

  constructor(private settings: SettingsService) {
    this.showLogin = !this.logged;
    if(this.logged) {
      this.settings.load();
    }
  }

  switchMenu() {
    this.hideMenu = !this.hideMenu;
  }

  toLogIn() {
    this.showLogin = true;
  }

  closeLogIn() {
    this.showLogin = false;
  }

  get logged(): boolean {
    return localStorage.getItem("username") != null;
  }

  showSettings: boolean = false;

  openSettings() {
    this.showSettings = true;
  }

  closeSettings() {
    this.showSettings = false;
  }

  showTimeline: boolean = false;

  openTimeline() {
    this.showTimeline = true;
  }

  closeTimeline() {
    this.showTimeline = false;
  }
}
