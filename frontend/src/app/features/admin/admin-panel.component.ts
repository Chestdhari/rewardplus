import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../../core/services/admin.service';
import { User, Role, AuditLog } from '../../core/models/user.model';

@Component({
  selector: 'app-admin-panel',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container" style="max-width: 1400px; padding: 0;">
      <h1 style="font-size: 1.75rem; margin-bottom: 2rem;">System Administration Control</h1>

      <!-- Navigation Tabs -->
      <div class="d-flex gap-3" style="border-bottom: 2px solid var(--border-color); margin-bottom: 1.5rem; padding-bottom: 0.5rem;">
        <button
          (click)="activeTab = 'users'"
          [ngStyle]="{
            color: activeTab === 'users' ? 'var(--primary-color)' : 'var(--text-secondary)',
            borderBottom: activeTab === 'users' ? '3px solid var(--primary-color)' : 'none'
          }"
          style="background: none; border: none; font-weight: 600; padding: 0.5rem 1rem; cursor: pointer;"
        >
          User Accounts
        </button>
        <button
          (click)="activeTab = 'audit'"
          [ngStyle]="{
            color: activeTab === 'audit' ? 'var(--primary-color)' : 'var(--text-secondary)',
            borderBottom: activeTab === 'audit' ? '3px solid var(--primary-color)' : 'none'
          }"
          style="background: none; border: none; font-weight: 600; padding: 0.5rem 1rem; cursor: pointer;"
        >
          System Audit Logs
        </button>
      </div>

      <!-- Users Tab Content -->
      <div *ngIf="activeTab === 'users'" class="grid grid-cols-3 gap-4">
        
        <!-- Left Panel: Add User Form -->
        <div class="rewardplus-card" style="height: fit-content;">
          <div class="card-header">
            <h3>Register Staff Account</h3>
          </div>
          
          <form (ngSubmit)="createUser()" style="margin-top: 1rem;">
            <div class="form-group">
              <label>Username *</label>
              <input type="text" [(ngModel)]="newUser.username" name="uUsername" required />
            </div>

            <div class="form-group">
              <label>Email Address *</label>
              <input type="email" [(ngModel)]="newUser.email" name="uEmail" required />
            </div>

            <div class="form-group">
              <label>Password *</label>
              <input type="password" [(ngModel)]="newUser.password" name="uPassword" required />
            </div>

            <div class="grid grid-cols-2 gap-2">
              <div class="form-group">
                <label>First Name</label>
                <input type="text" [(ngModel)]="newUser.firstName" name="uFirst" />
              </div>
              <div class="form-group">
                <label>Last Name</label>
                <input type="text" [(ngModel)]="newUser.lastName" name="uLast" />
              </div>
            </div>

            <div class="form-group">
              <label>System Role *</label>
              <select [(ngModel)]="newUser.roleName" name="uRole">
                <option value="ADMIN">ADMIN</option>
                <option value="MANAGER">MANAGER</option>
                <option value="SALES">SALES</option>
                <option value="MARKETING">MARKETING</option>
              </select>
            </div>

            <button type="submit" class="btn-primary" style="width: 100%; justify-content: center; margin-top: 1rem;">
              <span class="material-icons">person_add</span>
              Create Staff User
            </button>
          </form>
        </div>

        <!-- Right Panel: Users Table -->
        <div style="grid-column: span 2;" class="rewardplus-card">
          <div class="card-header">
            <h3>Configured User Accounts</h3>
          </div>
          <div style="overflow-x: auto;">
            <table class="enterprise-table">
              <thead>
                <tr>
                  <th>User Details</th>
                  <th>Email</th>
                  <th>Role Badge</th>
                  <th>Status</th>
                  <th style="width: 40px;"></th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let u of users">
                  <td>
                    <strong>{{ u.firstName }} {{ u.lastName }}</strong>
                    <p style="font-size: 0.75rem; color: var(--text-secondary);">\u0040{{ u.username }}</p>
                  </td>
                  <td>{{ u.email }}</td>
                  <td>
                    <span class="badge" [ngClass]="getUserRoleName(u).toLowerCase()">
                      {{ getUserRoleName(u) }}
                    </span>
                  </td>
                  <td>
                    <span class="badge" [ngClass]="u.active ? 'badge-success' : 'badge-danger'">
                      {{ u.active ? 'Active' : 'Disabled' }}
                    </span>
                  </td>
                  <td>
                    <button (click)="deleteUser(u.id!)" style="border: none; background: none; color: var(--danger-color); cursor: pointer;" [disabled]="u.username === 'admin'">
                      <span class="material-icons" style="font-size: 1.125rem;">delete</span>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

      </div>

      <!-- Audit Logs Tab Content -->
      <div *ngIf="activeTab === 'audit'" class="rewardplus-card">
        <div class="card-header">
          <h3>Operations Audit Trail</h3>
          <span style="font-size: 0.825rem; color: var(--text-secondary);">Immutable log of all user security activities</span>
        </div>

        <div style="overflow-x: auto; margin-top: 1rem;">
          <table class="enterprise-table">
            <thead>
              <tr>
                <th>Log ID</th>
                <th>Operator</th>
                <th>Action</th>
                <th>Target Resource</th>
                <th>Details</th>
                <th>Timestamp</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let log of auditLogs">
                <td>#{{ log.id }}</td>
                <td><strong>{{ log.username }}</strong></td>
                <td>
                  <span class="badge badge-primary">{{ log.action }}</span>
                </td>
                <td>{{ log.entityName }} (ID: {{ log.entityId || 'N/A' }})</td>
                <td style="font-size: 0.75rem;">{{ log.description }}</td>
                <td>{{ log.createdAt | date:'medium' }}</td>
              </tr>
              <tr *ngIf="auditLogs.length === 0">
                <td colspan="6" style="text-align: center; color: var(--text-muted); padding: 2rem;">
                  No system audit logs found.
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `
})
export class AdminPanelComponent implements OnInit {
  activeTab: 'users' | 'audit' = 'users';
  
  users: User[] = [];
  roles: Role[] = [];
  auditLogs: AuditLog[] = [];

  newUser = {
    username: '',
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    roleName: 'SALES'
  };

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.adminService.getAllUsers().subscribe({
      next: (users) => this.users = users,
      error: (err) => console.error('Error fetching users', err)
    });

    this.adminService.getAllRoles().subscribe({
      next: (roles) => this.roles = roles,
      error: (err) => console.error('Error fetching roles', err)
    });

    this.adminService.getAllAuditLogs().subscribe({
      next: (logs) => this.auditLogs = logs,
      error: (err) => console.error('Error fetching audit logs', err)
    });
  }

  getUserRoleName(user: User): string {
    if (!user.roles || user.roles.length === 0) return 'NONE';
    return user.roles[0].roleName;
  }

  createUser() {
    if (!this.newUser.username || !this.newUser.email || !this.newUser.password) return;

    const matchingRole = this.roles.find(r => r.roleName === this.newUser.roleName);
    if (!matchingRole) {
      alert('Selected system role is not loaded yet.');
      return;
    }

    const payload = {
      username: this.newUser.username,
      email: this.newUser.email,
      password: this.newUser.password,
      firstName: this.newUser.firstName,
      lastName: this.newUser.lastName,
      roleId: matchingRole.id,
      active: true
    };

    this.adminService.createUser(payload).subscribe({
      next: () => {
        this.loadData();
        this.newUser = {
          username: '',
          email: '',
          password: '',
          firstName: '',
          lastName: '',
          roleName: 'SALES'
        };
      },
      error: (err) => alert(err.error?.message || 'Failed to create staff account.')
    });
  }

  deleteUser(id: number) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.adminService.deleteUser(id).subscribe({
        next: () => this.loadData(),
        error: (err) => alert('Failed to delete user.')
      });
    }
  }
}
