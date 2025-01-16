// file: src/app/features/auth/register/register.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../../../MaterialModule';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  username = '';
  password = '';
  email = '';
  bio = '';
  errorMessage = '';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  onSubmit() {
    const body = {
      username: this.username,
      password: this.password,
      email: this.email,
      bio: this.bio,
      profilePhoto: null
    };

    this.http.post<any>('http://localhost:8080/api/auth/register', body)
      .subscribe({
        next: (res) => {
          if (res && res.jwt) {
            // после регистрации отправим на /login
            this.router.navigate(['/login']);
          }
        },
        error: (err) => {
          this.errorMessage = err?.error?.message || 'Ошибка регистрации';
        }
      });
  }
}
