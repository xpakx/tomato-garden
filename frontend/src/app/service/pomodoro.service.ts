import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pomodoro } from '../component/entity/pomodoro';
import { StartRequest } from '../component/entity/start-request';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PomodoroService {
  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  private getUserId() {
    return localStorage.getItem("user_id");
  }

  public start(request: StartRequest):  Observable<Pomodoro> {
    let userId  = this.getUserId();
    return this.http.post<Pomodoro>(`${this.apiServerUrl}/${userId}/pomodoro`, request);
  }

  public stop(pomodoroId: number):  Observable<Pomodoro> {
    let userId  = this.getUserId();
    return this.http.put<Pomodoro>(`${this.apiServerUrl}/${userId}/pomodoro/${pomodoroId}/stop`, null);
  }
}
