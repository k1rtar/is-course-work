// file: src/app/features/auth/login/login.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { MaterialModule } from '../../../../MaterialModule';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}

  onSubmit() {
    const body = {
      username: this.username,
      password: this.password
    };

    this.http.post<any>('http://localhost:8080/api/auth/login', body)
      .subscribe({
        next: (res) => {
          if (res && res.jwt) {
            this.authService.setToken(res.jwt);
            // Нужно запросить user, чтобы получить isBlocked
            this.http.get<any>(`http://localhost:8080/api/auth/users/${this.username}`).subscribe({
              next: (user) => {
                this.authService.setBlocked(user.isBlocked);
                this.router.navigate(['/']);
              },
              error: () => {
                this.router.navigate(['/']);
              }
            });
          }
        },
        error: (err) => {
          this.errorMessage = err?.error?.message || 'Ошибка входа';
        }
      });
  }
}
