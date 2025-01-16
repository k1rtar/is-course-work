// file: src/app/features/tasks/task-list/task-list.component.ts
import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskService, Task } from '../../../core/services/task.service';
import { RouterLink } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../MaterialModule';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule, MaterialModule],
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})
export class TaskListComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['title', 'difficultyLevel', 'isPublic', 'actions'];
  dataSource = new MatTableDataSource<Task>([]);

  // Фильтры
  searchTitle: string = '';
  minDifficulty?: number;
  maxDifficulty?: number;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadTasks() {
    this.taskService.searchTasks(
      this.searchTitle,
      undefined, // Если нужно добавить categoryId, замените undefined на соответствующее значение
      this.minDifficulty,
      this.maxDifficulty
    ).subscribe({
      next: (res: Task[]) => {
        this.dataSource.data = res;
      },
      error: (err) => {
        console.error('Ошибка при загрузке задач:', err);
      }
    });
  }

  applyFilter() {
    this.loadTasks();
  }

  onSortChange(sortState: any) {
    // MatTableDataSource автоматически обрабатывает сортировку
    // Поэтому дополнительная обработка не требуется
  }

  onPageChange(event: any) {
    // MatTableDataSource автоматически обрабатывает пагинацию
    // Поэтому дополнительная обработка не требуется
  }
}
