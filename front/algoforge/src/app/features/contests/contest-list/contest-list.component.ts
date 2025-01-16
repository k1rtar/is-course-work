import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContestService, Contest } from '../../../core/services/contest.service';
import { MaterialModule } from '../../../../MaterialModule';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-contest-list',
  standalone: true,
  imports: [CommonModule, MaterialModule, RouterLink],
  templateUrl: './contest-list.component.html',
  styleUrls: ['./contest-list.component.scss']
})
export class ContestListComponent implements OnInit {
  contests: Contest[] = [];

  constructor(private contestService: ContestService) {}

  ngOnInit(): void {
    // получим публичные контесты
    this.contestService.getPublicContests().subscribe({
      next: (res) => this.contests = res,
      error: (err) => console.error(err)
    });
  }
}
