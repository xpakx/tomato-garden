import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'tomato-garden';
  hideMenu: boolean = true;
  showLogin: boolean = false;

  switchMenu() {
    this.hideMenu = !this.hideMenu;
  }

  toLogIn() {
    this.showLogin = true;
  }

  closeLogIn() {
    this.showLogin = false;
  }
}
