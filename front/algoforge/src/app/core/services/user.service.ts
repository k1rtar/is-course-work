// file: src/app/core/services/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  id: number;
  email: string;
  username: string;
  bio: string;
  profilePhoto: any;
  creationDate: string;
  rating: number;
  isBlocked: boolean;
  isDeleted: boolean;
  roles: string[];
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiBaseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.apiBaseUrl}/users/${username}`);
  }

  blockUser(username: string): Observable<User> {
    return this.http.put<User>(`${this.apiBaseUrl}/users/${username}/block`, {});
  }

  unblockUser(username: string): Observable<User> {
    return this.http.put<User>(`${this.apiBaseUrl}/users/${username}/unblock`, {});
  }
}
