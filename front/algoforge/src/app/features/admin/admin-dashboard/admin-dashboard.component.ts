// file: src/app/features/admin/admin-dashboard/admin-dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../MaterialModule';
import { AdminService } from '../../../core/services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
  userToDelete = '';
  taskIdToDelete!: number | null;
  contestIdToDelete!: number | null;

  blockUsername = '';
  unblockUsername = '';

  message = '';

  constructor(
    private adminService: AdminService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {}

  deleteUser() {
    if (this.userToDelete) {
      this.adminService.deleteUser(this.userToDelete).subscribe({
        next: () => {
          this.snackBar.open(`Пользователь "${this.userToDelete}" удалён`, 'OK', { duration: 3000 });
        },
        error: (err) => {
          this.snackBar.open(`Ошибка удаления пользователя`, 'OK', { duration: 3000 });
        }
      });
    }
  }

  deleteTask() {
    if (this.taskIdToDelete) {
      this.adminService.deleteTask(this.taskIdToDelete).subscribe({
        next: () => {
          this.snackBar.open(`Задача #${this.taskIdToDelete} удалена`, 'OK', { duration: 3000 });
        },
        error: (err) => {
          this.snackBar.open(`Ошибка удаления задачи`, 'OK', { duration: 3000 });
        }
      });
    }
  }

  deleteContest() {
    if (this.contestIdToDelete) {
      this.adminService.deleteContest(this.contestIdToDelete).subscribe({
        next: () => {
          this.snackBar.open(`Контест #${this.contestIdToDelete} удалён`, 'OK', { duration: 3000 });
        },
        error: (err) => {
          this.snackBar.open(`Ошибка удаления контеста`, 'OK', { duration: 3000 });
        }
      });
    }
  }

  doBlockUser() {
    if (!this.blockUsername) return;
    this.adminService.blockUser(this.blockUsername).subscribe({
      next: () => {
        this.snackBar.open(`Пользователь "${this.blockUsername}" заблокирован`, 'OK', { duration: 3000 });
      },
      error: () => {
        this.snackBar.open(`Ошибка блокировки`, 'OK', { duration: 3000 });
      }
    });
  }

  doUnblockUser() {
    if (!this.unblockUsername) return;
    this.adminService.unblockUser(this.unblockUsername).subscribe({
      next: () => {
        this.snackBar.open(`Пользователь "${this.unblockUsername}" разблокирован`, 'OK', { duration: 3000 });
      },
      error: () => {
        this.snackBar.open(`Ошибка разблокировки`, 'OK', { duration: 3000 });
      }
    });
  }
}
