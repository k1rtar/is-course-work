// file: src/app/core/services/auth.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private tokenKey = 'jwt-token';
  private blockedKey = 'is-blocked'; // признак блокировки

  constructor() {}

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  clearToken(): void {
    localStorage.removeItem(this.tokenKey);
  }

  setBlocked(isBlocked: boolean) {
    localStorage.setItem(this.blockedKey, String(isBlocked));
  }

  isBlockedUser(): boolean {
    return localStorage.getItem(this.blockedKey) === 'true';
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  hasAnyRole(roles: string[]): boolean {
    // Для упрощения: если не заблокирован, то ок.
    // Либо можно декодировать JWT, смотреть claim roles, проверять пересечение
    return !this.isBlockedUser();
  }
}
