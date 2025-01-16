// file: src/app/features/tasks/my-tasks/my-tasks.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskService } from '../../../core/services/task.service';
import { Task } from '../../../models/task.model';
import { RouterLink } from '@angular/router';
import { MaterialModule } from '../../../../MaterialModule';

@Component({
  selector: 'app-my-tasks',
  standalone: true,
  imports: [CommonModule, RouterLink, MaterialModule],
  templateUrl: './my-tasks.component.html',
  styleUrls: ['./my-tasks.component.scss']
})
export class MyTasksComponent implements OnInit {
  tasks: Task[] = [];

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadMyTasks();
  }

  loadMyTasks(): void {
    this.taskService.getMyTasks().subscribe({
      next: (res: Task[]) => {
        this.tasks = res;
      },
      error: (err: any) => {
        console.error(err);
      }
    });
  }
}
