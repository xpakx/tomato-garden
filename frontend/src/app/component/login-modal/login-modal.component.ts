import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { Token } from '../../entity/token';

@Component({
  selector: 'app-login-modal',
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.css']
})
export class LoginModalComponent implements OnInit {
  loginForm: FormGroup;
  registerForm: FormGroup;
  public invalid: boolean = false;
  public message: string = '';
  @Output() closeEvent = new EventEmitter<boolean>();
  showRegister: boolean = false;

  constructor(private fb: FormBuilder, private service: AuthenticationService) { 
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      passwordRe: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  get valid(): boolean {
    return this.showRegister ? this.registerForm.valid : this.loginForm.valid;
  }

  get title(): string {
    return this.showRegister ? "Register" : "Login";
  }

  ok() {
    if(!this.valid) {
      return;
    }
    if(this.showRegister) {
      this.signUp();
    } else {
      this.logIn();
    }
  }

  close() {
    this.closeEvent.emit(false);
  }

  toRegister(): void {
    this.showRegister = true;
  }

  toLogin(): void {
    this.showRegister = false;
  }

  logIn(): void {
    if(this.loginForm.valid) {
      this.invalid = false;
      this.service.authenticate({
        username: this.loginForm.controls.username.value,
        password: this.loginForm.controls.password.value
      }).subscribe(
        (response: Token) => {
          this.saveCredentials(response);
        },
        (error: HttpErrorResponse) => {
          this.message = error.error.message;
          this.invalid = true;
        }
      )
    } else {
      this.message = "Fields cannot be empty!";
      this.invalid = true;
    }
  }

  signUp(): void {
    if(this.registerForm.valid) {
      this.invalid = false;
      this.service.register({
        username: this.registerForm.controls.username.value,
        password: this.registerForm.controls.password.value,
        passwordRe: this.registerForm.controls.passwordRe.value,
      }).subscribe(
        (response: Token) => {
          this.saveCredentials(response);
        },
        (error: HttpErrorResponse) => {
          this.message = error.error.message;
          this.invalid = true;
        }
      )
    } else {
      this.message = "Fields cannot be empty!";
      this.invalid = true;
    }
  }

  private saveCredentials(response: Token) {
    localStorage.setItem("token", response.token);
    localStorage.setItem("username", response.username);
    this.closeEvent.emit(true);
  }
}
