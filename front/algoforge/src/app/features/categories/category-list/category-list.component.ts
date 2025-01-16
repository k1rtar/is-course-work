// // import { Component, OnInit } from '@angular/core';
// // import { CommonModule } from '@angular/common';
// // import { MaterialModule } from '../../../../MaterialModule'; // если есть
// // import { FormsModule } from '@angular/forms';
// // import { CategoryService } from '../../../core/services/category.service';
// // import { Category } from '../../../models/category.model';
// // import {MatCardModule} from '@angular/material/card';
// //
// // @Component({
// //   selector: 'app-category-list',
// //   standalone: true,
// //   imports: [CommonModule, FormsModule, MaterialModule, MatCardModule],
// //   template: `
// // <mat-card>
// //   <mat-card-title>Categories</mat-card-title>
// //   <mat-card-content>
// //     <div>
// //       <mat-form-field>
// //         <mat-label>New Category Name</mat-label>
// //         <input matInput [(ngModel)]="newCategoryName">
// //       </mat-form-field>
// //       <mat-form-field>
// //         <mat-label>Description</mat-label>
// //         <input matInput [(ngModel)]="newCategoryDesc">
// //       </mat-form-field>
// //       <button mat-raised-button color="primary" (click)="createCategory()">Create Category</button>
// //     </div>
// //     <hr/>
// //     <table mat-table [dataSource]="categories" class="mat-elevation-z2">
// //       <ng-container matColumnDef="categoryName">
// //         <th mat-header-cell *matHeaderCellDef>Category Name</th>
// //         <td mat-cell *matCellDef="let c">{{ c.categoryName }}</td>
// //       </ng-container>
// //       <ng-container matColumnDef="description">
// //         <th mat-header-cell *matHeaderCellDef>Description</th>
// //         <td mat-cell *matCellDef="let c">{{ c.description }}</td>
// //       </ng-container>
// //
// //       <tr mat-header-row *matHeaderRowDef="displayedCols"></tr>
// //       <tr mat-row *matRowDef="let row; columns: displayedCols;"></tr>
// //     </table>
// //   </mat-card-content>
// // </mat-card>
// //   `,
// //   styles: [`
// //     mat-card { margin: 1rem; }
// //     table { width: 100%; }
// //   `]
// // })
// // export class CategoryListComponent implements OnInit {
// //   categories: Category[] = [];
// //   displayedCols = ['categoryName', 'description'];
// //
// //   newCategoryName = '';
// //   newCategoryDesc = '';
// //
// //   constructor(private categoryService: CategoryService) {}
// //
// //   ngOnInit(): void {
// //     this.loadCategories();
// //   }
// //
// //   loadCategories(): void {
// //     this.categoryService.getAllCategories().subscribe({
// //       next: (res) => { this.categories = res; },
// //       error: (err) => console.error(err)
// //     });
// //   }
// //
// //   createCategory() {
// //     if (!this.newCategoryName.trim()) return;
// //     const cat: Partial<Category> = {
// //       categoryName: this.newCategoryName.trim(),
// //       description: this.newCategoryDesc.trim()
// //     };
// //     this.categoryService.createCategory(cat as Category).subscribe({
// //       next: () => {
// //         this.newCategoryName = '';
// //         this.newCategoryDesc = '';
// //         this.loadCategories();
// //       },
// //       error: (err) => console.error(err)
// //     });
// //   }
// // }
// import { Component, OnInit } from '@angular/core';
// import { CategoryService, Category } from '../../../core/services/category.service';
// import { Router } from '@angular/router';
//
// @Component({
//   selector: 'app-category-list',
//   templateUrl: './category-list.component.html',
//   standalone: true,
//   styleUrls: ['./category-list.component.css']
// })
// export class CategoryListComponent implements OnInit {
//   categories: Category[] = [];
//   selectedCategory?: Category;
//
//   constructor(private categoryService: CategoryService, private router: Router) { }
//
//   ngOnInit(): void {
//     this.loadCategories();
//   }
//
//   // loadCategories(): void {
//   //   this.categoryService.getCategories().subscribe(
//   //     data => this.categories = data,
//   //     error => console.error(error)
//   //   );
//   // }
//
//   onSelect(category: Category): void {
//     this.selectedCategory = category;
//   }
//
//   // onDelete(categoryId: number): void {
//   //   if (confirm('Вы уверены, что хотите удалить эту категорию?')) {
//   //     this.categoryService.deleteCategory(categoryId).subscribe(
//   //       () => this.loadCategories(),
//   //       error => console.error(error)
//   //     );
//   //   }
//   // }
//
//   navigateToEdit(category: Category): void {
//     this.router.navigate(['/categories/edit', category.categoryId]);
//   }
// }
