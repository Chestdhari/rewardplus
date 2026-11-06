import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  template: `
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-header">
          <span class="material-icons logo-icon">loyalty</span>
          <h2>RewardPlus Retailers</h2>
          <p>Sign in to your Loyalty portal</p>
        </div>

        <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">
          <!-- Error alert banner -->
          <div *ngIf="errorMessage" class="alert-banner error">
            <span class="material-icons">error</span>
            <div class="alert-content">{{ errorMessage }}</div>
          </div>

          <div class="form-group">
            <label for="username">Username</label>
            <input
              type="text"
              id="username"
              formControlName="username"
              placeholder="Enter your username"
            />
            <div *ngIf="loginForm.get('username')?.touched && loginForm.get('username')?.invalid" class="error-text">
              Username is required.
            </div>
          </div>

          <div class="form-group">
            <label for="password">Password</label>
            <input
              type="password"
              id="password"
              formControlName="password"
              placeholder="Enter your password"
            />
            <div *ngIf="loginForm.get('password')?.touched && loginForm.get('password')?.invalid" class="error-text">
              Password is required (min 6 characters).
            </div>
          </div>

          <button type="submit" class="btn-primary" style="width: 100%; justify-content: center; margin-top: 1rem;" [disabled]="loginForm.invalid || isLoading">
            <span *ngIf="isLoading" class="material-icons spinning">sync</span>
            {{ isLoading ? 'Signing In...' : 'Sign In' }}
          </button>
        </form>

        <div style="text-align: center; margin-top: 1.5rem; font-size: 0.875rem;">
          <p>Are you a customer? <a routerLink="/register" style="color: var(--primary-color); font-weight: 600; text-decoration: none;">Enroll Now</a></p>
        </div>
      </div>
    </div>
  `
})
export class LoginComponent {
  loginForm: FormGroup;
  isLoading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login(this.loginForm.value).subscribe({
      next: (res) => {
        this.isLoading = false;
        // Redirect based on role
        this.redirectUser(res.role);
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'Invalid username or password. Please try again.';
      }
    });
  }

  private redirectUser(role: string) {
    if (role === 'CUSTOMER') {
      this.router.navigate(['/customer/dashboard']);
    } else if (role === 'SALES') {
      this.router.navigate(['/sales/pos']);
    } else if (role === 'MARKETING') {
      this.router.navigate(['/marketing/promotions']);
    } else if (role === 'MANAGER') {
      this.router.navigate(['/manager/dashboard']);
    } else if (role === 'ADMIN') {
      this.router.navigate(['/admin/users']);
    } else {
      this.router.navigate(['/']);
    }
  }
}
