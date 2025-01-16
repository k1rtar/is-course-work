// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { ContestService } from '../../../core/services/contest.service';
// import { ActivatedRoute, Router } from '@angular/router';
// import { MaterialModule } from '../../../../MaterialModule';
// import { MatSnackBar } from '@angular/material/snack-bar';
// import { AuthService } from '../../../core/services/auth.service';
//
// @Component({
//   selector: 'app-contest-participate',
//   standalone: true,
//   imports: [CommonModule, MaterialModule],
//   templateUrl: './contest-participate.component.html',
//   styleUrls: ['./contest-participate.component.scss']
// })
// export class ContestParticipateComponent implements OnInit {
//   contestId!: number;
//
//   constructor(
//     private route: ActivatedRoute,
//     private contestService: ContestService,
//     private router: Router,
//     private snackBar: MatSnackBar,
//     private authService: AuthService
//   ) {}
//
//   ngOnInit(): void {
//     this.contestId = Number(this.route.snapshot.paramMap.get('id'));
//   }
//
//   participate() {
//     const userId = this.authService.getUserId();
//     if (!userId) {
//       this.snackBar.open('Пожалуйста, войдите в систему', 'OK', { duration: 3000 });
//       this.router.navigate(['/login']);
//       return;
//     }
//
//     this.contestService.registerParticipant(this.contestId, userId).subscribe({
//       next: () => {
//         this.snackBar.open('Вы успешно зарегистрировались на контест', 'OK', { duration: 3000 });
//         this.router.navigate(['/contests', this.contestId]);
//       },
//       error: () => {
//         this.snackBar.open('Ошибка регистрации на контест', 'OK', { duration: 3000 });
//       }
//     });
//   }
// }
