// file: src/app/features/contests/contest-tasks/contest-tasks.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskService, Task } from '../../../core/services/task.service';
import { ContestService } from '../../../core/services/contest.service';
import { ActivatedRoute } from '@angular/router';
import { MaterialModule } from '../../../../MaterialModule';
import { FormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-contest-tasks',
  standalone: true,
  imports: [CommonModule, MaterialModule, FormsModule],
  templateUrl: './contest-tasks.component.html',
  styleUrls: ['./contest-tasks.component.scss']
})
export class ContestTasksComponent implements OnInit {
  contestId!: number;
  allTasks: Task[] = [];
  selectedTaskIds: number[] = [];
  contestTasks: { contestId: number; taskId: number }[] = [];

  constructor(
    private taskService: TaskService,
    private contestService: ContestService,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.contestId = Number(this.route.snapshot.paramMap.get('id'));
    // Загрузим все публичные задачи, для примера
    this.taskService.getPublicTasks().subscribe({
      next: (res) => {
        this.allTasks = res;
      },
      error: (err) => console.error(err)
    });

    this.loadContestTasks();
  }

  loadContestTasks() {
    this.contestService.getContestTasks(this.contestId).subscribe({
      next: (res) => {
        this.contestTasks = res;
      },
      error: (err) => {
        this.snackBar.open('Не удалось загрузить задачи контеста', 'OK', { duration: 3000 });
      }
    });
  }

  onAddTasks() {
    if (this.selectedTaskIds.length === 0) return;
    this.contestService.addTasksToContest(this.contestId, this.selectedTaskIds).subscribe({
      next: () => {
        this.snackBar.open('Задачи добавлены в контест', 'OK', { duration: 3000 });
        this.selectedTaskIds = [];
        this.loadContestTasks();
      },
      error: () => {
        this.snackBar.open('Ошибка при добавлении задач', 'OK', { duration: 3000 });
      }
    });
  }
}
