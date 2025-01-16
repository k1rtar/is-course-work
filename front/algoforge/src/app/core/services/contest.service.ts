// src/app/core/services/contest.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Contest {
  contestId?: number;
  title: string;
  description: string;
  startTime: string;
  endTime: string;
  isPublic: boolean;
  creatorUserId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class ContestService {
  private apiBaseUrl = 'http://localhost:8080/api/contests';

  constructor(private http: HttpClient) {}

  getPublicContests(): Observable<Contest[]> {
    return this.http.get<Contest[]>(`${this.apiBaseUrl}/public`);
  }

  /**
   * Допустим, нужен метод searchPublicContests(title, page, size):
   * Но на бэке пока нет. При желании, реализуем client-side
   */
  searchPublicContests(title: string, page: number, size: number): Observable<any> {
    // Если бэкенд не умеет искать/пагинировать, нужно client-side.
    // Для примера: просто getPublicContests() и возвращаем mock PageResponse.
    return new Observable(observer => {
      this.getPublicContests().subscribe({
        next: (all) => {
          // client-side filter
          const filtered = all.filter(c => !title || c.title.toLowerCase().includes(title.toLowerCase()));
          const startIndex = page * size;
          const slice = filtered.slice(startIndex, startIndex + size);
          const totalElements = filtered.length;
          const totalPages = Math.ceil(totalElements / size);
          observer.next({
            content: slice,
            totalElements,
            totalPages,
            size,
            number: page
          });
          observer.complete();
        },
        error: (err) => observer.error(err)
      });
    });
  }

  getAllContests(): Observable<Contest[]> {
    return this.http.get<Contest[]>(`${this.apiBaseUrl}`);
  }

  getMyContests(): Observable<Contest[]> {
    // Предположим, на бэке нет отдельного метода. Нужно добавить,
    // либо фильтровать client-side. Для примера — client-side:
    return this.getAllContests();
  }

  getContestById(id: number): Observable<Contest> {
    return this.http.get<Contest>(`${this.apiBaseUrl}/${id}`);
  }

  createContest(contest: Contest): Observable<Contest> {
    return this.http.post<Contest>(`${this.apiBaseUrl}`, contest);
  }

  updateContest(id: number, contest: Contest): Observable<Contest> {
    return this.http.put<Contest>(`${this.apiBaseUrl}/${id}`, contest);
  }

  deleteContest(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiBaseUrl}/${id}`);
  }

  addTasksToContest(contestId: number, ids: number[]): Observable<void> {
    return this.http.post<void>(`${this.apiBaseUrl}/${contestId}/tasks`, ids);
  }

  registerParticipant(contestId: number, userId: number): Observable<void> {
    return this.http.post<void>(`${this.apiBaseUrl}/${contestId}/participants?userId=${userId}`, {});
  }

  getContestTasks(contestId: number): Observable<{ contestId: number; taskId: number }[]> {
    return this.http.get<{ contestId: number; taskId: number }[]>(`${this.apiBaseUrl}/${contestId}/tasks`);
  }

  getContestParticipants(contestId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/${contestId}/participants`);
  }

  // Заглушка для результатов
  getContestResults(contestId: number): Observable<any[]> {
    // На бэке нет реализации /results, это заглушка
    return new Observable(observer => {
      // например, mock
      observer.next([
        { userId: 2, username: "user1", score: 100, rank: 1 },
      ]);
      observer.complete();
    });
  }
}
