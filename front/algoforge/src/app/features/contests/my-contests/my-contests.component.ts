// file: src/app/features/contests/my-contests/my-contests.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContestService, Contest } from '../../../core/services/contest.service';
import { MaterialModule } from '../../../../MaterialModule';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-my-contests',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './my-contests.component.html',
  styleUrls: ['./my-contests.component.scss']
})
export class MyContestsComponent implements OnInit {
  myContests: Contest[] = [];

  constructor(
    private contestService: ContestService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    // Предположим, userId "скрыт" в JWT (не разобран).
    // Либо client-side: getAllContests -> filter by creator
    const userId = 123; // mock, у вас нет decode.
    // В реальности decode JWT or X-User-Id
    // Либо fetch from bэкенд ?

    this.contestService.getMyContests().subscribe({
      next: (all) => {
        // Filter
        // TODO: decode real userId from token
        const myId = 123; // stub
        this.myContests = all.filter(ct => ct.creatorUserId === myId);
      },
      error: (err) => console.error(err)
    });
  }
}
