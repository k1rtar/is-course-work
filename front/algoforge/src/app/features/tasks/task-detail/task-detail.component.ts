import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { TaskService, Task } from '../../../core/services/task.service';
import { MaterialModule } from '../../../../MaterialModule';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-task-detail',
  standalone: true,
  imports: [CommonModule, MaterialModule, FormsModule],
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.scss']
})
export class TaskDetailComponent implements OnInit {
  taskId!: number;
  task?: Task;

  // Для отправки решения:
  solutionCode: string = '';
  language: string = 'PYTHON'; // by default
  submissionResult: any;

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService,
    private http: HttpClient,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.taskId = Number(this.route.snapshot.paramMap.get('id'));
    this.taskService.getTaskById(this.taskId).subscribe({
      next: (res) => {
        this.task = res;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  onSendSolution() {
    // Вызываем solution-service: POST /api/solutions/submit
    // Тело: CreateSolutionRequest { taskId, language, code, (userId из JWT) }
    // userId на бэке через JWT
    const body = {
      taskId: this.taskId,
      language: this.language,
      code: this.solutionCode
    };
    this.http.post<any>('http://localhost:8080/api/solutions/submit', body)
      .subscribe({
        next: (res) => {
          this.submissionResult = res; // ExecutionResult
          if (res.allPassed) {
            this.snackBar.open('Решение прошло все тесты!', 'OK', { duration: 3000 });
          } else {
            this.snackBar.open('Некоторые тесты упали', 'OK', { duration: 5000 });
          }
        },
        error: (err) => {
          this.snackBar.open('Ошибка отправки решения', 'OK', { duration: 3000 });
        }
      });
  }
}
