import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'tomato-garden';
  hideMenu: boolean = true;
  showLogin: boolean = true;

  constructor() {
    this.showLogin = !this.logged;
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
    return localStorage.getItem("username") != undefined;
  }
}
