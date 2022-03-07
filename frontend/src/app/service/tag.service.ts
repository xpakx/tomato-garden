import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Tag } from '../entity/tag';
import { TagRequest } from '../entity/tag-request';

@Injectable({
  providedIn: 'root'
})
export class TagService {
  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  private getUsername() {
    return localStorage.getItem("username");
  }

  public getAll(): Observable<Tag[]> {
    let username  = this.getUsername();
    return this.http.get<Tag[]>(`${this.apiServerUrl}/${username}/tag`);
  }

  public add(request: TagRequest): Observable<Tag> {
    let username  = this.getUsername();
    return this.http.post<Tag>(`${this.apiServerUrl}/${username}/tag`, request);
  }
}
