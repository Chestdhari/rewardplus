import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  template: `
    <div class="auth-container" style="padding-top: 3rem; padding-bottom: 3rem;">
      <div class="auth-card" style="max-width: 600px;">
        <div class="auth-header">
          <span class="material-icons logo-icon">card_membership</span>
          <h2>Loyalty Program Enrollment</h2>
          <p>Register to earn loyalty points and redeem rewards</p>
        </div>

        <form [formGroup]="registerForm" (ngSubmit)="onSubmit()">
          <!-- Error banner -->
          <div *ngIf="errorMessage" class="alert-banner error">
            <span class="material-icons">error</span>
            <div class="alert-content">{{ errorMessage }}</div>
          </div>

          <div class="grid grid-cols-2 gap-3">
            <div class="form-group">
              <label for="firstName">First Name *</label>
              <input type="text" id="firstName" formControlName="firstName" placeholder="John" />
              <div *ngIf="registerForm.get('firstName')?.touched && registerForm.get('firstName')?.invalid" class="error-text">
                Required.
              </div>
            </div>
            
            <div class="form-group">
              <label for="lastName">Last Name *</label>
              <input type="text" id="lastName" formControlName="lastName" placeholder="Doe" />
              <div *ngIf="registerForm.get('lastName')?.touched && registerForm.get('lastName')?.invalid" class="error-text">
                Required.
              </div>
            </div>
          </div>

          <div class="grid grid-cols-2 gap-3">
            <div class="form-group">
              <label for="username">Username *</label>
              <input type="text" id="username" formControlName="username" placeholder="johndoe" />
              <div *ngIf="registerForm.get('username')?.touched && registerForm.get('username')?.invalid" class="error-text">
                Required.
              </div>
            </div>

            <div class="form-group">
              <label for="email">Email *</label>
              <input type="email" id="email" formControlName="email" placeholder="john@example.com" />
              <div *ngIf="registerForm.get('email')?.touched && registerForm.get('email')?.invalid" class="error-text">
                Enter a valid email.
              </div>
            </div>
          </div>

          <div class="grid grid-cols-2 gap-3">
            <div class="form-group">
              <label for="password">Password *</label>
              <input type="password" id="password" formControlName="password" placeholder="••••••••" />
              <div *ngIf="registerForm.get('password')?.touched && registerForm.get('password')?.invalid" class="error-text">
                Min 6 characters.
              </div>
            </div>

            <div class="form-group">
              <label for="phone">Phone Number *</label>
              <input type="text" id="phone" formControlName="phone" placeholder="10-digit number" />
              <div *ngIf="registerForm.get('phone')?.touched && registerForm.get('phone')?.invalid" class="error-text">
                Required (10 digits).
              </div>
            </div>
          </div>

          <div class="grid grid-cols-2 gap-3">
            <div class="form-group">
              <label for="dateOfBirth">Date of Birth</label>
              <input type="date" id="dateOfBirth" formControlName="dateOfBirth" />
            </div>

            <div class="form-group">
              <label for="pinCode">ZIP / PIN Code</label>
              <input type="text" id="pinCode" formControlName="pinCode" placeholder="10001" />
            </div>
          </div>

          <div class="form-group">
            <label for="address">Street Address</label>
            <input type="text" id="address" formControlName="address" placeholder="123 Main St" />
          </div>

          <div class="grid grid-cols-3 gap-2">
            <div class="form-group">
              <label for="city">City</label>
              <input type="text" id="city" formControlName="city" placeholder="Metropolis" />
            </div>
            
            <div class="form-group">
              <label for="state">State</label>
              <input type="text" id="state" formControlName="state" placeholder="NY" />
            </div>

            <div class="form-group">
              <label for="country">Country</label>
              <input type="text" id="country" formControlName="country" placeholder="USA" />
            </div>
          </div>

          <button type="submit" class="btn-primary" style="width: 100%; justify-content: center; margin-top: 1rem;" [disabled]="registerForm.invalid || isLoading">
            <span *ngIf="isLoading" class="material-icons spinning">sync</span>
            {{ isLoading ? 'Enrolling...' : 'Enroll & Sign In' }}
          </button>
        </form>

        <div style="text-align: center; margin-top: 1.5rem; font-size: 0.875rem;">
          <p>Already registered? <a routerLink="/login" style="color: var(--primary-color); font-weight: 600; text-decoration: none;">Sign In here</a></p>
        </div>
      </div>
    </div>
  `
})
export class RegisterComponent {
  registerForm: FormGroup;
  isLoading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      dateOfBirth: [''],
      address: [''],
      city: [''],
      state: [''],
      country: ['USA'],
      pinCode: ['']
    });
  }

  onSubmit() {
    if (this.registerForm.invalid) return;

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.register(this.registerForm.value).subscribe({
      next: (res) => {
        this.isLoading = false;
        this.router.navigate(['/customer/dashboard']);
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'Registration failed. Phone or Username may already be in use.';
      }
    });
  }
}
