import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../MaterialModule';
import { ContestService, Contest } from '../../../core/services/contest.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-contest-create',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './contest-create.component.html',
  styleUrls: ['./contest-create.component.scss']
})
export class ContestCreateComponent {
  contestData: Contest = {
    title: '',
    description: '',
    startTime: '',
    endTime: '',
    isPublic: true
  };

  constructor(
    private contestService: ContestService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  onSubmit() {
    this.contestService.createContest(this.contestData).subscribe({
      next: (res) => {
        this.snackBar.open('Контест создан!', 'OK', { duration: 3000 });
        this.router.navigate(['/contests', res.contestId]);
      },
      error: (err) => {
        this.snackBar.open('Ошибка создания контеста', 'OK', { duration: 3000 });
      }
    });
  }
}
