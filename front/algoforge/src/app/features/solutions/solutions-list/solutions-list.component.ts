import { Component, OnInit, ViewChild } from '@angular/core';
import { SolutionService, Solution, TestResult } from '../../../core/services/solution.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../MaterialModule';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { RouterLink } from '@angular/router';

interface DisplaySolution extends Solution {
  testResults?: TestResult[];
}

@Component({
  selector: 'app-solutions-list',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule, RouterLink],
  templateUrl: './solutions-list.component.html',
  styleUrls: ['./solutions-list.component.scss']
})
export class SolutionsListComponent implements OnInit {

  displayedColumns: string[] = ['id', 'taskId', 'language', 'status', 'createdAt', 'actions'];
  dataSource = new MatTableDataSource<DisplaySolution>();
  expandedSolution: DisplaySolution | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private solutionService: SolutionService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.fetchSolutions();
  }

  fetchSolutions(): void {
    this.solutionService.getUserSolutions().subscribe({
      next: (solutions) => {
        const displaySolutions: DisplaySolution[] = solutions.map(solution => {
          let testResults: TestResult[] = [];
          if (solution.testResultsJson) {
            try {
              testResults = JSON.parse(solution.testResultsJson);
            } catch (e) {
              console.error('Error parsing testResultsJson', e);
            }
          }
          return { ...solution, testResults };
        });
        this.dataSource.data = displaySolutions;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (err) => {
        console.error('Error fetching solutions', err);
        this.snackBar.open('Ошибка при загрузке решений', 'Закрыть', { duration: 3000 });
      }
    });
  }

  // Удаляем toggleRow и isExpanded, так как теперь детали отображаются в отдельном компоненте

}
