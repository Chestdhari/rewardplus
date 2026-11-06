import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="dashboard-layout">
      <!-- Sidebar Navigation -->
      <aside class="sidebar">
        <div class="sidebar-header">
          <span class="material-icons logo-icon">loyalty</span>
          <h1>RewardPlus</h1>
        </div>

        <nav class="sidebar-nav">
          <!-- Customer Links -->
          <ng-container *ngIf="userRole === 'CUSTOMER'">
            <a routerLink="/customer/dashboard" routerLinkActive="active" class="nav-item">
              <span class="material-icons">dashboard</span>
              <span>My Loyalty Portal</span>
            </a>
          </ng-container>

          <!-- Sales Links -->
          <ng-container *ngIf="userRole === 'SALES' || userRole === 'ADMIN'">
            <a routerLink="/sales/pos" routerLinkActive="active" class="nav-item">
              <span class="material-icons">point_of_sale</span>
              <span>POS Checkout</span>
            </a>
          </ng-container>

          <!-- Marketing Links -->
          <ng-container *ngIf="userRole === 'MARKETING' || userRole === 'ADMIN'">
            <a routerLink="/marketing/promotions" routerLinkActive="active" class="nav-item">
              <span class="material-icons">campaign</span>
              <span>Promotions & Segments</span>
            </a>
          </ng-container>

          <!-- Manager Links -->
          <ng-container *ngIf="userRole === 'MANAGER' || userRole === 'ADMIN'">
            <a routerLink="/manager/dashboard" routerLinkActive="active" class="nav-item">
              <span class="material-icons">analytics</span>
              <span>Executive Dashboard</span>
            </a>
          </ng-container>

          <!-- Admin Links -->
          <ng-container *ngIf="userRole === 'ADMIN'">
            <a routerLink="/admin/users" routerLinkActive="active" class="nav-item">
              <span class="material-icons">manage_accounts</span>
              <span>User Accounts</span>
            </a>
          </ng-container>
        </nav>

        <!-- Sidebar Footer containing User profile summary -->
        <div class="sidebar-footer">
          <div class="user-info">
            <span class="username">{{ username }}</span>
            <span class="role badge" [ngClass]="userRole.toLowerCase()">{{ userRole }}</span>
          </div>
          <button (click)="logout()" class="logout-btn" title="Sign Out">
            <span class="material-icons">logout</span>
          </button>
        </div>
      </aside>

      <!-- Main Shell Content -->
      <div class="main-content">
        <!-- Header Topbar -->
        <header class="top-bar">
          <div>
            <h2 style="font-size: 1.25rem; font-weight: 500; color: var(--text-secondary);">
              Loyalty Management System
            </h2>
          </div>
          <div class="d-flex align-center gap-3">
            <span class="material-icons" style="color: var(--text-muted);">notifications</span>
            <div style="width: 1px; height: 24px; background-color: var(--border-color);"></div>
            <span style="font-size: 0.875rem; font-weight: 500;">RewardPlus Retailers Ltd.</span>
          </div>
        </header>

        <!-- Nested Component Body -->
        <main class="content-body">
          <router-outlet></router-outlet>
        </main>
      </div>
    </div>
  `
})
export class LayoutComponent {
  constructor(private authService: AuthService, private router: Router) {}

  get username(): string {
    return this.authService.currentUserSignal()?.username || 'User';
  }

  get userRole(): string {
    return this.authService.userRole();
  }

  logout() {
    this.authService.logout();
  }
}
