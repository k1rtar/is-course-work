
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TestResult {
  inputData: string;
  expectedOutput: string;
  actualOutput: string;
  passed: boolean;
  errorMessage?: string;
}

export interface ExecutionResult {
  allPassed: boolean;
  testResults: TestResult[];
  errorMessage?: string;
}

export interface Solution {
  id: number;
  taskId: number;
  userId: number;
  status: string; // CREATED, RUNNING, ACCEPTED, REJECTED
  language: string; // e.g., "java", "python"
  code: string;
  createdAt: string;
  errorMessage?: string;
  testResultsJson?: string; // JSON string of test results
}

@Injectable({
  providedIn: 'root'
})
export class SolutionService {

  private apiUrl = 'http://localhost:8080/api/solutions';

  constructor(private http: HttpClient) { }

  getUserSolutions(): Observable<Solution[]> {
    return this.http.get<Solution[]>(this.apiUrl);
  }

  getSolutionById(id: number): Observable<Solution> {
    return this.http.get<Solution>(`${this.apiUrl}/${id}`);
  }
}
