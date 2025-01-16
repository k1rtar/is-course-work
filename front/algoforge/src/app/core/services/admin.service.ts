import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AlgoUserDto {
  id: number;
  email: string;
  username: string;
  bio?: string;
  profilePhoto?: string; // base64 или URL
  creationDate: string; // ISO string
  rating: number;
  isBlocked: boolean;
  isDeleted: boolean;
  roles: string[];
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient) { }

  blockUser(username: string): Observable<AlgoUserDto> {
    return this.http.put<AlgoUserDto>(`${this.apiUrl}/users/${username}/block`, null);
  }

  unblockUser(username: string): Observable<AlgoUserDto> {
    return this.http.put<AlgoUserDto>(`${this.apiUrl}/users/${username}/unblock`, null);
  }

  deleteUser(username: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/users/${username}`);
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/tasks/${id}`);
  }

  deleteContest(contestId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/contests/${contestId}`);
  }
}
