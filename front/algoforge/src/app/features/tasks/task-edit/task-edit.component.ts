import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../MaterialModule';
import { TaskService, Task } from '../../../core/services/task.service';
import { CategoryService, Category } from '../../../core/services/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-task-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './task-edit.component.html',
  styleUrls: ['./task-edit.component.scss']
})
export class TaskEditComponent implements OnInit {
  taskId!: number;
  taskData?: Task;
  categoriesList: Category[] = [];
  selectedCategories: number[] = [];
  difficulties = [
    { label: 'Лёгкая (EASY)', value: 0 },
    { label: 'Средняя (MEDIUM)', value: 1 },
    { label: 'Сложная (HARD)', value: 2 }
  ];

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService,
    private categoryService: CategoryService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.taskId = Number(this.route.snapshot.paramMap.get('id'));
    this.categoryService.getAllCategories().subscribe({
      next: (cats) => {
        this.categoriesList = cats;
      }
    });

    this.taskService.getTaskById(this.taskId).subscribe({
      next: (res) => {
        this.taskData = res;
        // Заполним selectedCategories
        if (this.taskData.categories) {
          this.selectedCategories = this.taskData.categories.map(c => c.categoryId);
        }
      },
      error: (err) => {
        this.snackBar.open('Ошибка загрузки задачи', 'OK', { duration: 3000 });
      }
    });
  }

  onSubmit() {
    if (!this.taskData) return;
    this.taskData.categories = this.selectedCategories.map(id => {
      const c = this.categoriesList.find(ct => ct.categoryId === id);
      return c ? c : {};
    });

    this.taskService.updateTask(this.taskId, this.taskData).subscribe({
      next: (res) => {
        this.snackBar.open('Задача обновлена', 'OK', { duration: 3000 });
        this.router.navigate(['/tasks', res.id]);
      },
      error: (err) => {
        this.snackBar.open('Ошибка обновления задачи', 'OK', { duration: 3000 });
      }
    });
  }
}
