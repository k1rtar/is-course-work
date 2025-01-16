// // import { Component, OnInit } from '@angular/core';
// // import { CommonModule } from '@angular/common';
// // import { ActivatedRoute, RouterLink } from '@angular/router';
// // import { ContestService, Contest } from '../../../core/services/contest.service';
// // import { MaterialModule } from '../../../../MaterialModule';
// //
// // @Component({
// //   selector: 'app-contest-detail',
// //   standalone: true,
// //   imports: [CommonModule, RouterLink, MaterialModule],
// //   templateUrl: './contest-detail.component.html',
// //   styleUrls: ['./contest-detail.component.scss']
// // })
// // export class ContestDetailComponent implements OnInit {
// //   contestId!: number;
// //   contest?: Contest;
// //
// //   constructor(
// //     private route: ActivatedRoute,
// //     private contestService: ContestService
// //   ) {}
// //
// //   ngOnInit(): void {
// //     this.contestId = Number(this.route.snapshot.paramMap.get('id'));
// //     this.contestService.getContestById(this.contestId).subscribe({
// //       next: (res) => {
// //         this.contest = res;
// //       },
// //       error: (err) => console.error(err)
// //     });
// //   }
// // }
// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { ActivatedRoute, RouterLink } from '@angular/router';
// import { ContestService, Contest } from '../../../core/services/contest.service';
// import { MaterialModule } from '../../../../MaterialModule';
// import { AuthService } from '../../../core/services/auth.service';
//
// @Component({
//   selector: 'app-contest-detail',
//   standalone: true,
//   imports: [CommonModule, RouterLink, MaterialModule],
//   templateUrl: './contest-detail.component.html',
//   styleUrls: ['./contest-detail.component.scss']
// })
// export class ContestDetailComponent implements OnInit {
//   contestId!: number;
//   contest?: Contest;
//
//   constructor(
//     private route: ActivatedRoute,
//     private contestService: ContestService,
//     private authService: AuthService
//   ) {}
//
//   ngOnInit(): void {
//     this.contestId = Number(this.route.snapshot.paramMap.get('id'));
//     this.contestService.getContestById(this.contestId).subscribe({
//       next: (res) => {
//         this.contest = res;
//       },
//       error: (err) => console.error(err)
//     });
//   }
//
//   canParticipate(): boolean {
//     // Условия: пользователь вошел, не админ, и еще не участвует
//     if (!this.authService.isLoggedIn() || this.authService.isAdmin()) {
//       return false;
//     }
//     return !this.authService.isParticipant(this.contestId);
//   }
// }
