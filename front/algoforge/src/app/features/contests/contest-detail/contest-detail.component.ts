// // file: src/app/features/contests/contest-detail/contest-detail.component.ts
// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { ActivatedRoute, RouterLink } from '@angular/router';
// import { FormsModule } from '@angular/forms';
// import { MaterialModule } from '../../../../MaterialModule';
// import { ContestService, Contest } from '../../../core/services/contest.service';
//
// @Component({
//   selector: 'app-contest-detail',
//   standalone: true,
//   imports: [CommonModule, FormsModule, MaterialModule, RouterLink],
//   templateUrl: './contest-detail.component.html',
//   styleUrls: ['./contest-detail.component.scss']
// })
// export class ContestDetailComponent implements OnInit {
//   contestId!: number;
//   contest?: Contest;
//   contestTasks: { contestId: number; id: number }[] = [];
//
//   constructor(
//     private route: ActivatedRoute,
//     private contestService: ContestService
//   ) {}
//
//   ngOnInit(): void {
//     this.contestId = Number(this.route.snapshot.paramMap.get('id'));
//
//     // 1) Загружаем сам контест
//     this.contestService.getContestById(this.contestId).subscribe({
//       next: (res) => {
//         this.contest = res;
//       },
//       error: (err) => {
//         console.error('Ошибка при загрузке контеста:', err);
//       }
//     });
//
//     // 2) Загружаем задачи (ContestTask)
//     this.contestService.getContestTasks(this.contestId).subscribe({
//       next: (tasks) => {
//         this.contestTasks = tasks;
//       },
//       error: (err) => {
//         console.error('Ошибка при загрузке задач контеста:', err);
//       }
//     });
//   }
// }
// file: src/app/features/contests/contest-detail/contest-detail.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MaterialModule } from '../../../../MaterialModule';
import { ContestService, Contest } from '../../../core/services/contest.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-contest-detail',
  standalone: true,
  imports: [CommonModule, MaterialModule, RouterLink],
  templateUrl: './contest-detail.component.html',
  styleUrls: ['./contest-detail.component.scss']
})
export class ContestDetailComponent implements OnInit {
  contestId!: number;
  contest?: Contest;
  contestTasks: { contestId: number; taskId: number }[] = [];

  constructor(
    private route: ActivatedRoute,
    private contestService: ContestService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.contestId = Number(idParam);
      this.fetchContest();
      this.fetchContestTasks();
    } else {
      this.snackBar.open('Неверный ID контеста', 'Закрыть', { duration: 3000 });
    }
  }

  fetchContest(): void {
    this.contestService.getContestById(this.contestId).subscribe({
      next: (res) => {
        this.contest = res;
      },
      error: (err) => {
        console.error('Ошибка при загрузке контеста:', err);
        this.snackBar.open('Ошибка загрузки контеста', 'Закрыть', { duration: 3000 });
      }
    });
  }

  fetchContestTasks(): void {
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
}
