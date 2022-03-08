import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pomodoro } from '../entity/pomodoro';
import { StartRequest } from '../entity/start-request';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PomodoroService {
  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  private getUsername() {
    return localStorage.getItem("username");
  }

  public start(request: StartRequest):  Observable<Pomodoro> {
    let username  = this.getUsername();
    return this.http.post<Pomodoro>(`${this.apiServerUrl}/${username}/pomodoro`, request);
  }

  public stop(pomodoroId: number):  Observable<Pomodoro> {
    let username  = this.getUsername();
    return this.http.put<Pomodoro>(`${this.apiServerUrl}/${username}/pomodoro/${pomodoroId}/stop`, null);
  }

  public cancel(pomodoroId: number):  Observable<Pomodoro> {
    let username  = this.getUsername();
    return this.http.put<Pomodoro>(`${this.apiServerUrl}/${username}/pomodoro/${pomodoroId}/cancel`, null);
  }

  public pause(pomodoroId: number):  Observable<Pomodoro> {
    let username  = this.getUsername();
    return this.http.put<Pomodoro>(`${this.apiServerUrl}/${username}/pomodoro/${pomodoroId}/pause`, null);
  }

  public restart(pomodoroId: number):  Observable<Pomodoro> {
    let username  = this.getUsername();
    return this.http.put<Pomodoro>(`${this.apiServerUrl}/${username}/pomodoro/${pomodoroId}/restart`, null);
  }
}
