// src/app/features/tasks/task-search/task-search.component.ts
import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService, Task, Category } from '../../../core/services/task.service';
import { CategoryService } from '../../../core/services/category.service';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MaterialModule } from '../../../../MaterialModule'; // Убедитесь, что MatPaginator импортирован

@Component({
  selector: 'app-task-search',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './task-search.component.html',
  styleUrls: ['./task-search.component.scss']
})
export class TaskSearchComponent implements OnInit {
  // Параметры фильтра
  title: string = '';
  categoryId: number | null = null;
  minDifficulty: number | null = null;
  maxDifficulty: number | null = null;

  // Параметры пагинации
  page: number = 0;
  size: number = 5;

  // Все задачи и отображаемые задачи
  allTasks: Task[] = [];
  tasks: Task[] = [];
  totalElements: number = 0; // Всего задач
  totalPages: number = 0;

  categories: Category[] = [];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private taskService: TaskService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    // Загрузим категории
    this.categoryService.getAllCategories().subscribe({
      next: (res: Category[]) => {
        this.categories = res;
      },
      error: (err: any) => console.error(err)
    });

    // Первый поиск
    this.onSearch();
  }

  onSearch(): void {
    this.page = 0; // сбросим на первую страницу
    if (this.paginator) {
      this.paginator.firstPage();
    }
    this.loadTasks();
  }

  loadTasks(): void {
    this.taskService.searchTasks(
      this.title,
      this.categoryId != null ? this.categoryId : undefined,
      this.minDifficulty != null ? this.minDifficulty : undefined,
      this.maxDifficulty != null ? this.maxDifficulty : undefined
    ).subscribe({
      next: (res: Task[]) => {
        this.allTasks = res;
        this.totalElements = this.allTasks.length;
        this.totalPages = Math.ceil(this.totalElements / this.size);
        this.updateDisplayedTasks();
      },
      error: (err) => console.error('Ошибка при поиске задач:', err)
    });
  }

  updateDisplayedTasks(): void {
    const start = this.page * this.size;
    const end = start + this.size;
    this.tasks = this.allTasks.slice(start, end);
  }

  onPaginatorChange(event: PageEvent): void {
    this.page = event.pageIndex;
    this.size = event.pageSize;
    this.updateDisplayedTasks();
  }

  getDifficultyLabel(level: number): string {
    switch (level) {
      case 0:
        return 'EASY';
      case 1:
        return 'MEDIUM';
      case 2:
        return 'HARD';
      default:
        return 'UNKNOWN';
    }
  }
}
