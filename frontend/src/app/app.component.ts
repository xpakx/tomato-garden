import { animate, style, transition, trigger, group, query } from '@angular/animations';
import { Component } from '@angular/core';
import { SettingsService } from './service/settings.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    trigger('slideMenuAnimation', [
      transition(':enter', [
        style({ width: '0px', overflow: 'hidden' }),
        group([
          animate('.15s cubic-bezier(.4,0,1,1)', style({ width: '!' })),
          query('.menu-content',[
            style({ transform: 'translateX(-110%)' }),
            animate('.15s cubic-bezier(.4,0,1,1)', style({ transform: 'translateX(0)' }))
          ])
        ])
      ]),
      transition(':leave', [
        style({ overflow: 'hidden' }),
        group([
          animate('.15s cubic-bezier(.4,0,1,1)', style({ width: '0' })),
          query('.menu-content',[
            style({ transform: 'translateX(0)' }),
            animate('.15s cubic-bezier(.4,0,1,1)', style({ transform: 'translateX(-110%)' }))
          ])
        ])
      ]),
    ])
  ]
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

  switchMenu(): void {
    this.hideMenu = !this.hideMenu;
  }

  toLogIn(): void {
    this.showLogin = true;
  }

  closeLogIn(): void {
    this.showLogin = false;
  }

  get logged(): boolean {
    return localStorage.getItem("username") != null;
  }

  showSettings: boolean = false;

  openSettings(): void {
    this.showSettings = true;
  }

  closeSettings(): void {
    this.showSettings = false;
  }

  showTimeline: boolean = false;

  openTimeline(): void {
    this.showTimeline = true;
  }

  closeTimeline(): void {
    this.showTimeline = false;
  }

  showGarden: boolean = false;

  openGarden(): void {
    this.showGarden = true;
  }

  closeGarden(): void {
    this.showGarden = false;
  }
}
