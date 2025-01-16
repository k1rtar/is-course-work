// file: src/app/features/contests/contest-results/contest-results.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ContestService } from '../../../core/services/contest.service';
import { MaterialModule } from '../../../../MaterialModule';

@Component({
  selector: 'app-contest-results',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './contest-results.component.html',
  styleUrls: ['./contest-results.component.scss']
})
export class ContestResultsComponent implements OnInit {
  contestId!: number;
  results: any[] = [];
  displayedColumns = ['rank', 'username', 'score'];

  constructor(
    private route: ActivatedRoute,
    private contestService: ContestService
  ) {}

  ngOnInit(): void {
    this.contestId = Number(this.route.snapshot.paramMap.get('id'));
    this.contestService.getContestResults(this.contestId).subscribe({
      next: (res) => {
        this.results = res;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}
