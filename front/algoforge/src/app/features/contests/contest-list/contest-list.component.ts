// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { ContestService, Contest } from '../../../core/services/contest.service';
// import { MaterialModule } from '../../../../MaterialModule';
// import { RouterLink } from '@angular/router';
//
// @Component({
//   selector: 'app-contest-list',
//   standalone: true,
//   imports: [CommonModule, MaterialModule, RouterLink],
//   templateUrl: './contest-list.component.html',
//   styleUrls: ['./contest-list.component.scss']
// })
// export class ContestListComponent implements OnInit {
//   contests: Contest[] = [];
//
//   constructor(private contestService: ContestService) {}
//
//   ngOnInit(): void {
//     // получим публичные контесты
//     this.contestService.getPublicContests().subscribe({
//       next: (res) => this.contests = res,
//       error: (err) => console.error(err)
//     });
//   }
// }
// file: src/app/features/contests/contest-list/contest-list.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContestService, Contest } from '../../../core/services/contest.service';
import { MaterialModule } from '../../../../MaterialModule';
import { RouterLink } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-contest-list',
  standalone: true,
  imports: [CommonModule, MaterialModule, RouterLink],
  templateUrl: './contest-list.component.html',
  styleUrls: ['./contest-list.component.scss']
})
export class ContestListComponent implements OnInit {
  contests: Contest[] = [];

  constructor(
    private contestService: ContestService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.contestService.getAllContests().subscribe({
      next: (res) => {
        this.contests = res;
      },
      error: (err) => {
        console.error('Ошибка при загрузке контестов:', err);
        this.snackBar.open('Ошибка загрузки контестов', 'Закрыть', { duration: 3000 });
      }
    });
  }
}
