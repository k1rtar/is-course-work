// file: src/app/core/services/category.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Category {
  categoryId: number;
  categoryName: string;
  description?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiBaseUrl = 'http://localhost:8080/api/tasks/categories';

  constructor(private http: HttpClient) {}

  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiBaseUrl}`);
  }

  createCategory(category: Partial<Category>): Observable<Category> {
    return this.http.post<Category>(`${this.apiBaseUrl}`, category);
  }


}
