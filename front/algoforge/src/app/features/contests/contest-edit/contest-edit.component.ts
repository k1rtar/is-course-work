import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../MaterialModule';
import { ContestService, Contest } from '../../../core/services/contest.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-contest-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './contest-edit.component.html',
  styleUrls: ['./contest-edit.component.scss']
})
export class ContestEditComponent implements OnInit {
  contestId!: number;
  contestData?: Contest;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private contestService: ContestService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.contestId = Number(this.route.snapshot.paramMap.get('id'));
    this.contestService.getContestById(this.contestId).subscribe({
      next: (res) => {
        this.contestData = res;
      },
      error: () => {
        this.snackBar.open('Ошибка загрузки контеста', 'OK', { duration: 3000 });
      }
    });
  }

  onSubmit() {
    if (!this.contestData) return;
    this.contestService.updateContest(this.contestId, this.contestData).subscribe({
      next: (res) => {
        this.snackBar.open('Контест обновлён', 'OK', { duration: 3000 });
        this.router.navigate(['/contests', res.contestId]);
      },
      error: (err) => {
        this.snackBar.open('Ошибка обновления контеста', 'OK', { duration: 3000 });
      }
    });
  }
}
