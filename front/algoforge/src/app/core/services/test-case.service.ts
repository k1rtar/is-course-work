import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TestCase {
  testCaseId?: number;
  inputData: string;
  expectedOutputData: string;
  isSample: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class TestCaseService {
  private apiBaseUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  getTestCasesByid(id: number): Observable<TestCase[]> {
    return this.http.get<TestCase[]>(`${this.apiBaseUrl}/${id}/testcases`);
  }

  createTestCase(id: number, testCase: TestCase): Observable<TestCase> {
    return this.http.post<TestCase>(`${this.apiBaseUrl}/${id}/testcases`, testCase);
  }

  updateTestCase(id: number, testCaseId: number, testCase: TestCase): Observable<TestCase> {
    return this.http.put<TestCase>(`${this.apiBaseUrl}/${id}/testcases/${testCaseId}`, testCase);
  }

  deleteTestCase(id: number, testCaseId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiBaseUrl}/${id}/testcases/${testCaseId}`);
  }
}
