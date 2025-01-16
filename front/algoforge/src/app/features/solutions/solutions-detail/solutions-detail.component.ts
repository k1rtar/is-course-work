// src/app/features/solutions/solution-detail/solution-detail.component.ts

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { SolutionService, Solution, TestResult } from '../../../core/services/solution.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../../MaterialModule';

@Component({
  selector: 'app-solution-detail',
  standalone: true,
  imports: [CommonModule, MaterialModule, RouterLink],
  templateUrl: './solutions-detail.component.html',
  styleUrls: ['./solutions-detail.component.scss']
})
export class SolutionDetailComponent implements OnInit {

  solutionId!: number;
  solution?: Solution;
  testResults: TestResult[] = [];
  isLoading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private solutionService: SolutionService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.solutionId = +idParam;
        this.fetchSolution();
      } else {
        this.snackBar.open('Неверный ID решения', 'Закрыть', { duration: 3000 });
      }
    });
  }

  fetchSolution(): void {
    this.solutionService.getSolutionById(this.solutionId).subscribe({
      next: (solution) => {
        this.solution = solution;
        if (solution.testResultsJson) {
          try {
            this.testResults = JSON.parse(solution.testResultsJson);
          } catch (e) {
            console.error('Ошибка при парсинге testResultsJson', e);
            this.snackBar.open('Ошибка при обработке результатов тестов', 'Закрыть', { duration: 3000 });
          }
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Ошибка при получении решения', err);
        this.snackBar.open('Ошибка при загрузке решения', 'Закрыть', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

}
