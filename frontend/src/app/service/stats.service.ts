import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { StatsResponse } from '../entity/stats-response';
import { Page } from '../entity/page';
import { PomodoroMin } from '../entity/pomodoro-min';
import { StatsRequest } from '../entity/stats-request';

@Injectable({
  providedIn: 'root'
})
export class StatsService {
  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  private getUsername() {
    return localStorage.getItem("username");
  }

  public getStats(request: StatsRequest):  Observable<StatsResponse> {
    let username  = this.getUsername();
    return this.http.post<StatsResponse>(`${this.apiServerUrl}/${username}/stats`, request);
  }

  public getTimeline():  Observable<Page<PomodoroMin>> {
    let username  = this.getUsername();
    return this.http.get<Page<PomodoroMin>>(`${this.apiServerUrl}/${username}/timeline`);
  }

  public getTimelineByPage(page: number):  Observable<Page<PomodoroMin>> {
    let username  = this.getUsername();
    return this.http.get<Page<PomodoroMin>>(`${this.apiServerUrl}/${username}/timeline/${page}`);
  }
}
