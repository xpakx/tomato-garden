import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Settings } from '../entity/settings';
import { SettingsRequest } from '../entity/settings-request';

@Injectable({
  providedIn: 'root'
})
export class DefaultSettingsService {
  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  private getUsername() {
    return localStorage.getItem("username");
  }

  public changeSettings(request: SettingsRequest):  Observable<Settings> {
    let username  = this.getUsername();
    return this.http.put<Settings>(`${this.apiServerUrl}/${username}/settings`, request);
  }

  public getSettings():  Observable<Settings> {
    let username  = this.getUsername();
    return this.http.get<Settings>(`${this.apiServerUrl}/${username}/settings`);
  }
}
