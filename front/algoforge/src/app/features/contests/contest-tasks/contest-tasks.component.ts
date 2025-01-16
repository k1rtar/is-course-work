// // // file: src/app/features/contests/contest-tasks/contest-tasks.component.ts
// // import { Component, OnInit } from '@angular/core';
// // import { CommonModule } from '@angular/common';
// // import { TaskService, Task } from '../../../core/services/task.service';
// // import { ContestService } from '../../../core/services/contest.service';
// // import {ActivatedRoute, RouterLink} from '@angular/router';
// // import { MaterialModule } from '../../../../MaterialModule';
// // import { FormsModule } from '@angular/forms';
// // import { MatSnackBar } from '@angular/material/snack-bar';
// //
// // @Component({
// //   selector: 'app-contest-tasks',
// //   standalone: true,
// //   imports: [CommonModule, MaterialModule, FormsModule, RouterLink],
// //   templateUrl: './contest-tasks.component.html',
// //   styleUrls: ['./contest-tasks.component.scss']
// // })
// // export class ContestTasksComponent implements OnInit {
// //   contestId!: number;
// //   allTasks: Task[] = [];
// //   selectedTaskIds: number[] = [];
// //   contestTasks: { contestId: number; taskId: number }[] = [];
// //
// //   constructor(
// //     private taskService: TaskService,
// //     private contestService: ContestService,
// //     private route: ActivatedRoute,
// //     private snackBar: MatSnackBar
// //   ) {}
// //
// //   ngOnInit(): void {
// //     this.contestId = Number(this.route.snapshot.paramMap.get('id'));
// //     // Загрузим все публичные задачи, для примера
// //     this.taskService.getPublicTasks().subscribe({
// //       next: (res) => {
// //         this.allTasks = res;
// //       },
// //       error: (err) => console.error(err)
// //     });
// //
// //     this.loadContestTasks();
// //   }
// //
// //   loadContestTasks() {
// //     this.contestService.getContestTasks(this.contestId).subscribe({
// //       next: (res) => {
// //         this.contestTasks = res;
// //       },
// //       error: (err) => {
// //         this.snackBar.open('Не удалось загрузить задачи контеста', 'OK', { duration: 3000 });
// //       }
// //     });
// //   }
// //
// //   onAddTasks() {
// //     if (this.selectedTaskIds.length === 0) return;
// //     this.contestService.addTasksToContest(this.contestId, this.selectedTaskIds).subscribe({
// //       next: () => {
// //         this.snackBar.open('Задачи добавлены в контест', 'OK', { duration: 3000 });
// //         this.selectedTaskIds = [];
// //         this.loadContestTasks();
// //       },
// //       error: () => {
// //         this.snackBar.open('Ошибка при добавлении задач', 'OK', { duration: 3000 });
// //       }
// //     });
// //   }
// // }
// // file: src/app/features/contests/contest-tasks/contest-tasks.component.ts
//
// // import { Component, OnInit } from '@angular/core';
// // import { CommonModule } from '@angular/common';
// // import { TaskService, Task } from '../../../core/services/task.service';
// // import { ContestService } from '../../../core/services/contest.service';
// // import {ActivatedRoute, RouterLink} from '@angular/router';
// // import { MaterialModule } from '../../../../MaterialModule';
// // import { FormsModule } from '@angular/forms';
// // import { MatSnackBar } from '@angular/material/snack-bar';
// //
// // @Component({
// //   selector: 'app-contest-tasks',
// //   standalone: true,
// //   imports: [CommonModule, MaterialModule, FormsModule, RouterLink],
// //   templateUrl: './contest-tasks.component.html',
// //   styleUrls: ['./contest-tasks.component.scss']
// // })
// // export class ContestTasksComponent implements OnInit {
// //   contestId!: number;
// //   allTasks: Task[] = [];
// //   selectedTaskIds: number[] = [];
// //   contestTasks: { contestId: number; taskId: number }[] = [];
// //
// //   constructor(
// //     private taskService: TaskService,
// //     private contestService: ContestService,
// //     private route: ActivatedRoute,
// //     private snackBar: MatSnackBar
// //   ) {}
// //
// //   ngOnInit(): void {
// //     const idParam = this.route.snapshot.paramMap.get('id');
// //     if (idParam) {
// //       this.contestId = Number(idParam);
// //       this.loadAllTasks();
// //       this.loadContestTasks();
// //     } else {
// //       this.snackBar.open('Неверный ID контеста', 'Закрыть', { duration: 3000 });
// //     }
// //   }
// //
// //   loadAllTasks(): void {
// //     // Предполагаем, что есть метод для получения всех задач
// //     this.taskService.getAllTasks().subscribe({
// //       next: (res) => {
// //         this.allTasks = res;
// //       },
// //       error: (err) => {
// //         console.error('Ошибка при загрузке всех задач:', err);
// //         this.snackBar.open('Ошибка загрузки задач', 'Закрыть', { duration: 3000 });
// //       }
// //     });
// //   }
// //
// //   loadContestTasks(): void {
// //     this.contestService.getContestTasks(this.contestId).subscribe({
// //       next: (res) => {
// //         this.contestTasks = res;
// //       },
// //       error: (err) => {
// //         console.error('Ошибка при загрузке задач контеста:', err);
// //         this.snackBar.open('Ошибка загрузки задач контеста', 'Закрыть', { duration: 3000 });
// //       }
// //     });
// //   }
// //
// //   onAddTasks(): void {
// //     if (this.selectedTaskIds.length === 0) {
// //       this.snackBar.open('Выберите хотя бы одну задачу для добавления', 'OK', { duration: 3000 });
// //       return;
// //     }
// //
// //     this.contestService.addTasksToContest(this.contestId, this.selectedTaskIds).subscribe({
// //       next: () => {
// //         this.snackBar.open('Задачи добавлены в контест', 'OK', { duration: 3000 });
// //         this.selectedTaskIds = [];
// //         this.loadContestTasks();
// //       },
// //       error: (err) => {
// //         console.error('Ошибка при добавлении задач в контест:', err);
// //         this.snackBar.open('Ошибка при добавлении задач', 'OK', { duration: 3000 });
// //       }
// //     });
// //   }
// // }
// // src/app/features/contests/contest-tasks/contest-tasks.component.ts
//
// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { RouterLink } from '@angular/router'; // Импортируйте RouterLink
// import { MaterialModule } from '../../../../MaterialModule';
// import { FormsModule } from '@angular/forms';
// import { TaskService, Task } from '../../../core/services/task.service';
// import { ContestService } from '../../../core/services/contest.service';
// import { ActivatedRoute } from '@angular/router';
// import { MatSnackBar } from '@angular/material/snack-bar';
// import { Category } from '../../../models/category.model'; // Правильный импорт
//
// @Component({
//   selector: 'app-contest-tasks',
//   standalone: true,
//   imports: [CommonModule, MaterialModule, FormsModule, RouterLink], // Добавьте RouterLink в imports
//   templateUrl: './contest-tasks.component.html',
//   styleUrls: ['./contest-tasks.component.scss']
// })
// export class ContestTasksComponent implements OnInit {
//   contestId!: number;
//   allTasks: Task[] = [];
//   selectedTaskIds: number[] = [];
//   contestTasks: { contestId: number; taskId: number }[] = [];
//
//   // Возможно, нужно добавить список всех категорий или других зависимостей
//
//   constructor(
//     private taskService: TaskService,
//     private contestService: ContestService,
//     private route: ActivatedRoute,
//     private snackBar: MatSnackBar
//   ) {}
//
//   ngOnInit(): void {
//     const idParam = this.route.snapshot.paramMap.get('id');
//     if (idParam) {
//       this.contestId = Number(idParam);
//       this.loadAllTasks();
//       this.loadContestTasks();
//     } else {
//       this.snackBar.open('Неверный ID контеста', 'Закрыть', { duration: 3000 });
//     }
//   }
//
//   loadAllTasks(): void {
//     this.taskService.getAllTasks().subscribe({
//       next: (res) => {
//         this.allTasks = res;
//       },
//       error: (err) => {
//         console.error('Ошибка при загрузке всех задач:', err);
//         this.snackBar.open('Ошибка загрузки задач', 'Закрыть', { duration: 3000 });
//       }
//     });
//   }
//
//   loadContestTasks(): void {
//     this.contestService.getContestTasks(this.contestId).subscribe({
//       next: (res) => {
//         this.contestTasks = res;
//       },
//       error: (err) => {
//         console.error('Ошибка при загрузке задач контеста:', err);
//         this.snackBar.open('Ошибка загрузки задач контеста', 'Закрыть', { duration: 3000 });
//       }
//     });
//   }
//
//   onAddTasks(): void {
//     if (this.selectedTaskIds.length === 0) {
//       this.snackBar.open('Выберите хотя бы одну задачу для добавления', 'OK', { duration: 3000 });
//       return;
//     }
//
//     this.contestService.addTasksToContest(this.contestId, this.selectedTaskIds).subscribe({
//       next: () => {
//         this.snackBar.open('Задачи добавлены в контест', 'OK', { duration: 3000 });
//         this.selectedTaskIds = [];
//         this.loadContestTasks();
//       },
//       error: (err) => {
//         console.error('Ошибка при добавлении задач в контест:', err);
//         this.snackBar.open('Ошибка при добавлении задач', 'OK', { duration: 3000 });
//       }
//     });
//   }
// }
// src/app/features/contests/contest-tasks/contest-tasks.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MaterialModule } from '../../../../MaterialModule';
import { FormsModule } from '@angular/forms';
import { TaskService, Task } from '../../../core/services/task.service';
import { ContestService } from '../../../core/services/contest.service';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-contest-tasks',
  standalone: true,
  imports: [CommonModule, MaterialModule, FormsModule, RouterLink],
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
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.contestId = Number(idParam);
      this.loadAllTasks();
      this.loadContestTasks();
    } else {
      this.snackBar.open('Неверный ID контеста', 'Закрыть', { duration: 3000 });
    }
  }

  loadAllTasks(): void {
    this.taskService.getPublicTasks().subscribe({
      next: (res) => {
        this.allTasks = res;
      },
      error: (err) => {
        console.error('Ошибка при загрузке всех задач:', err);
        this.snackBar.open('Ошибка загрузки задач', 'Закрыть', { duration: 3000 });
      }
    });
  }

  loadContestTasks(): void {
    this.contestService.getContestTasks(this.contestId).subscribe({
      next: (res) => {
        this.contestTasks = res;
      },
      error: (err) => {
        console.error('Ошибка при загрузке задач контеста:', err);
        this.snackBar.open('Ошибка загрузки задач контеста', 'Закрыть', { duration: 3000 });
      }
    });
  }

  onAddTasks(): void {
    if (this.selectedTaskIds.length === 0) {
      this.snackBar.open('Выберите хотя бы одну задачу для добавления', 'OK', { duration: 3000 });
      return;
    }

    this.contestService.addTasksToContest(this.contestId, this.selectedTaskIds).subscribe({
      next: () => {
        this.snackBar.open('Задачи добавлены в контест', 'OK', { duration: 3000 });
        this.selectedTaskIds = [];
        this.loadContestTasks();
      },
      error: (err) => {
        console.error('Ошибка при добавлении задач в контест:', err);
        this.snackBar.open('Ошибка при добавлении задач', 'OK', { duration: 3000 });
      }
    });
  }
}
