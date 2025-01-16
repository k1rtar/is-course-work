// file: src/app/core/services/task.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Task {
  id?: number;
  title: string;
  description: string;
  difficultyLevel: number; // 0=EASY,1=MEDIUM,2=HARD (или ordinal)
  creationDate?: string;
  isPublic: boolean;
  status: string;
  maxExecutionTime?: number;
  maxMemoryUsage?: number;
  creatorUserId?: number;
  testCases?: any[];
  categories?: any[];
}

export interface Category {
  categoryId: number;
  categoryName: string;
  description?: string;
}


interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number; // текущая страница (0-based)
}

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiBaseUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  getPublicTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiBaseUrl}/public`);
  }

  getTaskById(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.apiBaseUrl}/${id}`);
  }

  createTask(task: Task): Observable<any> {
    return this.http.post<any>(`${this.apiBaseUrl}/create`, task);
  }

  updateTask(id: number, task: Task): Observable<Task> {
    return this.http.put<Task>(`${this.apiBaseUrl}/${id}`, task);
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiBaseUrl}/${id}`);
  }

  searchTasks(
    title?: string,
    categoryId?: number,
    minDifficulty?: number,
    maxDifficulty?: number,
    page: number = 0,
    size: number = 5
  ): Observable<Task []> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (title) {
      params = params.set('title', title);
    }
    if (categoryId != null) {
      params = params.set('categoryId', categoryId);
    }
    if (minDifficulty != null) {
      params = params.set('minDifficulty', minDifficulty);
    }
    if (maxDifficulty != null) {
      params = params.set('maxDifficulty', maxDifficulty);
    }

    return this.http.get<Task []>(`${this.apiBaseUrl}/search`, { params });
  }

  getMyTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiBaseUrl}/my`);
  }
}
