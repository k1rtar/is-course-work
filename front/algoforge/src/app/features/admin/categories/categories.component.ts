import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryService, Category } from '../../../core/services/category.service';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../MaterialModule';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-categories',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule, RouterLink],
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {
  categories: Category[] = [];
  newCategoryName = '';
  newCategoryDesc = '';

  constructor(
    private categoryService: CategoryService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (cats) => this.categories = cats,
      error: () => this.snackBar.open('Ошибка загрузки категорий', 'OK', { duration: 3000 })
    });
  }

  createCategory(): void {
    if (!this.newCategoryName.trim()) return;
    const payload = {
      categoryName: this.newCategoryName,
      description: this.newCategoryDesc
    };
    this.categoryService.createCategory(payload).subscribe({
      next: (cat) => {
        this.snackBar.open('Категория создана', 'OK', { duration: 3000 });
        this.newCategoryName = '';
        this.newCategoryDesc = '';
        this.loadCategories();
      },
      error: () => {
        this.snackBar.open('Ошибка создания категории', 'OK', { duration: 3000 });
      }
    });
  }

  // deleteCategory(categoryId: number): void {
  //   if (confirm('Вы уверены, что хотите удалить эту категорию?')) {
  //     this.categoryService.deleteCategory(categoryId).subscribe({
  //       next: () => {
  //         this.snackBar.open('Категория удалена', 'OK', { duration: 3000 });
  //         this.loadCategories();
  //       },
  //       error: () => {
  //         this.snackBar.open('Ошибка удаления категории', 'OK', { duration: 3000 });
  //       }
  //     });
  //   }
  // }
}
