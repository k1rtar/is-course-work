// src/app/features/tasks/task-detail/task-detail.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { TaskService, Task } from '../../../core/services/task.service';
import { MaterialModule } from '../../../../MaterialModule';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TestCaseDto } from '../../../models/test-case.dto';

@Component({
  selector: 'app-task-detail',
  standalone: true,
  imports: [CommonModule, MaterialModule, FormsModule],
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.scss']
})
export class TaskDetailComponent implements OnInit {
  id!: number;
  task?: Task;

  // Для отправки решения:
  solutionCode: string = '';
  language: string = 'PYTHON'; // по умолчанию
  submissionResult: any;

  // Фильтрованные тестовые случаи (isSample = true)
  sampleTestCases: TestCaseDto[] = [];

  // Свойство для управления состоянием загрузки
  isLoading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService,
    private http: HttpClient,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = Number(idParam);
      this.fetchTask();
    } else {
      this.snackBar.open('Неверный ID задачи', 'Закрыть', { duration: 3000 });
      this.isLoading = false;
    }
  }

  fetchTask(): void {
    this.taskService.getTaskById(this.id).subscribe({
      next: (res) => {
        this.task = res;
        if (this.task.testCases) {
          this.sampleTestCases = this.task.testCases.filter(tc => tc.isSample);
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error(err);
        this.snackBar.open('Ошибка при загрузке задачи', 'Закрыть', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  onSendSolution() {
    if (!this.task) {
      this.snackBar.open('Задача не загружена', 'Закрыть', { duration: 3000 });
      return;
    }

    // Проверка наличия кода решения
    if (!this.solutionCode.trim()) {
      this.snackBar.open('Пожалуйста, введите код решения', 'Закрыть', { duration: 3000 });
      return;
    }

    // Формируем тело запроса
    const body = {
      taskId: this.id,
      language: this.language,
      code: this.solutionCode
      // Предполагается, что сервер сам определит, какие тестовые случаи использовать
    };

    // Отправка решения
    this.http.post<any>('http://localhost:8080/api/solutions/submit', body)
      .subscribe({
        next: (res) => {
          // Фильтруем результаты, показывая только для sample тестов
          if (res.testResults) {
            res.testResults = res.testResults.filter((tr: any) => 
              this.sampleTestCases.some(tc => tc.testCaseId === tr.testCaseId)
            );
          }
          this.submissionResult = res; // ExecutionResult
          if (res.allPassed) {
            this.snackBar.open('Решение прошло все тесты!', 'OK', { duration: 3000 });
          } else {
            this.snackBar.open('Некоторые тесты упали', 'OK', { duration: 5000 });
          }
        },
        error: (err) => {
          console.error(err);
          this.snackBar.open('Ошибка отправки решения', 'OK', { duration: 3000 });
        }
      });
  }
}
