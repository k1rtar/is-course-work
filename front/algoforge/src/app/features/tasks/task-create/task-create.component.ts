import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../MaterialModule';
import { TaskService, Task } from '../../../core/services/task.service';
import { CategoryService, Category } from '../../../core/services/category.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-task-create',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.scss']
})
export class TaskCreateComponent implements OnInit {
  taskData: Task = {
    title: '',
    description: '',
    difficultyLevel: 0, // 0=EASY,1=MEDIUM,2=HARD
    maxExecutionTime: 1,
    maxMemoryUsage: 1,
    isPublic: false,
    status: 'Pending',
    categories: [],
    testCases: []
  };

  // Это наши поля
  categoriesList: Category[] = [];
  selectedCategories: number[] = [];
  difficulties = [
    { label: 'Лёгкая (EASY)', value: 0 },
    { label: 'Средняя (MEDIUM)', value: 1 },
    { label: 'Сложная (HARD)', value: 2 }
  ];

  // Массив для хранения локально создаваемых тест-кейсов
  testCases: Array<{
    inputData: string;
    expectedOutputData: string;
    isSample: boolean;
  }> = [];

  constructor(
    private taskService: TaskService,
    private categoryService: CategoryService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    // Загрузим список категорий
    this.categoryService.getAllCategories().subscribe({
      next: (cats) => {
        this.categoriesList = cats;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  addTestCase(): void {
    this.testCases.push({
      inputData: '',
      expectedOutputData: '',
      isSample: false
    });
  }

  removeTestCase(index: number): void {
    this.testCases.splice(index, 1);
  }

  onSubmit() {
    // Положим выбранные категории в taskData.categories
    this.taskData.categories = this.selectedCategories.map(id => {
      const c = this.categoriesList.find(ct => ct.categoryId === id);
      return c ? c : {};
    });

    // А теперь пропишем список тест-кейсов
    this.taskData.testCases = this.testCases;

    this.taskService.createTask(this.taskData).subscribe({
      next: () => {
        this.snackBar.open('Задача создана успешно!', 'OK', { duration: 3000 });
        this.router.navigate(['/tasks']);
      },
      error: () => {
        this.snackBar.open('Ошибка при создании задачи', 'OK', { duration: 3000 });
      }
    });
  }
}
