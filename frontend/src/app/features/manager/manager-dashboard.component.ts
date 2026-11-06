import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../core/services/dashboard.service';

@Component({
  selector: 'app-manager-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container" style="max-width: 1400px; padding: 0;">
      <!-- Title & Report Trigger -->
      <div class="d-flex justify-between align-center" style="margin-bottom: 2rem;">
        <div>
          <h1 style="font-size: 1.75rem; margin-bottom: 0.25rem;">Executive Business Intelligence</h1>
          <p>Real-time analytics and loyalty program performance metrics</p>
        </div>
        <button (click)="printReport()" class="btn-primary">
          <span class="material-icons">print</span>
          Generate Report
        </button>
      </div>

      <!-- KPI Metrics Grid -->
      <div class="grid grid-cols-4 gap-3" style="margin-bottom: 2.5rem;">
        <div class="rewardplus-card">
          <div class="d-flex justify-between align-center" style="margin-bottom: 0.5rem;">
            <p style="font-size: 0.875rem; font-weight: 500; color: var(--text-secondary);">Total Revenue</p>
            <span class="material-icons" style="color: var(--primary-color);">attach_money</span>
          </div>
          <h2 style="font-size: 2rem; font-weight: 700; color: var(--primary-color);">
            \${{ (stats.totalRevenue || 0) | number:'1.2-2' }}
          </h2>
          <p style="font-size: 0.75rem; color: var(--success-color); margin-top: 0.25rem; font-weight: 500;">
            <span class="material-icons" style="font-size: 0.875rem; vertical-align: text-bottom;">trending_up</span>
            Revenue from loyalty transactions
          </p>
        </div>

        <div class="rewardplus-card">
          <div class="d-flex justify-between align-center" style="margin-bottom: 0.5rem;">
            <p style="font-size: 0.875rem; font-weight: 500; color: var(--text-secondary);">Total Customers</p>
            <span class="material-icons" style="color: var(--success-color);">people</span>
          </div>
          <h2 style="font-size: 2rem; font-weight: 700;">
            {{ stats.totalCustomers }}
          </h2>
          <p style="font-size: 0.75rem; color: var(--text-secondary); margin-top: 0.25rem;">
            Active members: <strong>{{ stats.activeMembers }}</strong>
          </p>
        </div>

        <div class="rewardplus-card">
          <div class="d-flex justify-between align-center" style="margin-bottom: 0.5rem;">
            <p style="font-size: 0.875rem; font-weight: 500; color: var(--text-secondary);">Loyalty Points Issued</p>
            <span class="material-icons" style="color: var(--warning-color);">stars</span>
          </div>
          <h2 style="font-size: 2rem; font-weight: 700; color: var(--warning-color);">
            {{ stats.totalPointsIssued | number }}
          </h2>
          <p style="font-size: 0.75rem; color: var(--text-secondary); margin-top: 0.25rem;">
            Redeemed: <strong>{{ stats.totalPointsRedeemed | number }}</strong>
          </p>
        </div>

        <div class="rewardplus-card">
          <div class="d-flex justify-between align-center" style="margin-bottom: 0.5rem;">
            <p style="font-size: 0.875rem; font-weight: 500; color: var(--text-secondary);">Active Promotions</p>
            <span class="material-icons" style="color: #8b5cf6;">campaign</span>
          </div>
          <h2 style="font-size: 2rem; font-weight: 700; color: #8b5cf6;">
            {{ stats.activePromotions }}
          </h2>
          <p style="font-size: 0.75rem; color: var(--text-secondary); margin-top: 0.25rem;">
            Live marketing campaigns
          </p>
        </div>
      </div>

      <!-- Analytics Charts -->
      <div class="grid grid-cols-2 gap-4" style="margin-bottom: 2.5rem;">
        
        <!-- Donut/Bar Chart: Loyalty Tiers Distribution -->
        <div class="rewardplus-card">
          <div class="card-header">
            <h3>Loyalty Member Tiers Distribution</h3>
          </div>
          <div class="d-flex align-center justify-between" style="margin-top: 1.5rem; min-height: 220px;">
            <!-- Custom Horizontal SVG Chart -->
            <div style="flex: 1; display: flex; flex-direction: column; gap: 1rem; padding-right: 2rem;">
              <div>
                <div class="d-flex justify-between" style="font-size: 0.825rem; margin-bottom: 0.25rem;">
                  <span>Bronze</span>
                  <strong>{{ stats.loyaltyTiers.bronze }}</strong>
                </div>
                <svg width="100%" height="8">
                  <rect width="100%" height="8" rx="4" fill="#e2e8f0"/>
                  <rect [attr.width]="getPercentWidth(stats.loyaltyTiers.bronze)" height="8" rx="4" fill="#ffedd5"/>
                </svg>
              </div>

              <div>
                <div class="d-flex justify-between" style="font-size: 0.825rem; margin-bottom: 0.25rem;">
                  <span>Silver</span>
                  <strong>{{ stats.loyaltyTiers.silver }}</strong>
                </div>
                <svg width="100%" height="8">
                  <rect width="100%" height="8" rx="4" fill="#e2e8f0"/>
                  <rect [attr.width]="getPercentWidth(stats.loyaltyTiers.silver)" height="8" rx="4" fill="#f1f5f9"/>
                </svg>
              </div>

              <div>
                <div class="d-flex justify-between" style="font-size: 0.825rem; margin-bottom: 0.25rem;">
                  <span>Gold</span>
                  <strong>{{ stats.loyaltyTiers.gold }}</strong>
                </div>
                <svg width="100%" height="8">
                  <rect width="100%" height="8" rx="4" fill="#e2e8f0"/>
                  <rect [attr.width]="getPercentWidth(stats.loyaltyTiers.gold)" height="8" rx="4" fill="#fef9c3"/>
                </svg>
              </div>

              <div>
                <div class="d-flex justify-between" style="font-size: 0.825rem; margin-bottom: 0.25rem;">
                  <span>Platinum</span>
                  <strong>{{ stats.loyaltyTiers.platinum }}</strong>
                </div>
                <svg width="100%" height="8">
                  <rect width="100%" height="8" rx="4" fill="#e2e8f0"/>
                  <rect [attr.width]="getPercentWidth(stats.loyaltyTiers.platinum)" height="8" rx="4" fill="#fae8ff"/>
                </svg>
              </div>
            </div>

            <!-- Total display block -->
            <div style="width: 140px; text-align: center; border-left: 1px solid var(--border-color); padding-left: 1.5rem;">
              <h3 style="font-size: 2.25rem; font-weight: 700; color: var(--primary-color);">
                {{ stats.totalCustomers }}
              </h3>
              <p style="font-size: 0.75rem; color: var(--text-secondary); margin-top: 0.25rem;">Total Program Accounts</p>
            </div>
          </div>
        </div>

        <!-- Vertical SVG Bar Chart: Top Rewards Redeemed -->
        <div class="rewardplus-card">
          <div class="card-header">
            <h3>Top Redeemed Rewards</h3>
          </div>
          <div style="margin-top: 1rem; display: flex; align-items: flex-end; justify-content: space-around; height: 220px; padding-bottom: 1.5rem;">
            <div *ngFor="let item of stats.redemptionStats" style="display: flex; flex-direction: column; align-items: center; width: 60px;">
              <span style="font-size: 0.75rem; font-weight: 600; color: var(--primary-color); margin-bottom: 0.25rem;">
                {{ item.count }}
              </span>
              <!-- SVG bar -->
              <svg width="30" [attr.height]="item.count * 10 || 10">
                <rect width="24" [attr.height]="item.count * 30 || 10" rx="3" fill="var(--primary-color)"/>
              </svg>
              <span style="font-size: 0.75rem; color: var(--text-secondary); text-align: center; margin-top: 0.5rem; text-overflow: ellipsis; white-space: nowrap; overflow: hidden; width: 100%;" [title]="item.name">
                {{ item.name }}
              </span>
            </div>
            
            <div *ngIf="stats.redemptionStats.length === 0" style="width: 100%; text-align: center; color: var(--text-muted); font-size: 0.875rem; margin-top: 5rem;">
              No rewards redeemed yet.
            </div>
          </div>
        </div>

      </div>

      <!-- Recent Transactions Table -->
      <div class="rewardplus-card">
        <div class="card-header">
          <h3>Recent POS Purchase Transactions</h3>
        </div>
        <div style="overflow-x: auto; margin-top: 1rem;">
          <table class="enterprise-table">
            <thead>
              <tr>
                <th>Tx Number</th>
                <th>Customer ID</th>
                <th>Total Amount</th>
                <th>Status</th>
                <th>Items Count</th>
                <th>Transaction Date</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let tx of stats.recentTransactions">
                <td><strong style="color: var(--primary-color);">{{ tx.transactionNumber }}</strong></td>
                <td>Customer #{{ tx.customerId }}</td>
                <td>\${{ tx.totalAmount | number:'1.2-2' }}</td>
                <td>
                  <span class="badge badge-success">COMPLETED</span>
                </td>
                <td>{{ tx.items?.length || 0 }} products</td>
                <td>{{ tx.createdAt | date:'medium' }}</td>
              </tr>
              <tr *ngIf="stats.recentTransactions.length === 0">
                <td colspan="6" style="text-align: center; color: var(--text-muted); padding: 2rem;">
                  No transactions recorded.
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `
})
export class ManagerDashboardComponent implements OnInit {
  stats = {
    totalCustomers: 0,
    activeMembers: 0,
    totalRevenue: 0,
    totalPointsIssued: 0,
    totalPointsRedeemed: 0,
    activePromotions: 0,
    loyaltyTiers: { bronze: 0, silver: 0, gold: 0, platinum: 0 },
    recentTransactions: [] as any[],
    redemptionStats: [] as { name: string; count: number }[]
  };

  constructor(private dashboardService: DashboardService) {}

  ngOnInit() {
    this.loadStats();
  }

  loadStats() {
    this.dashboardService.getManagerDashboardData().subscribe({
      next: (data) => this.stats = data,
      error: (err) => console.error('Error fetching dashboard stats', err)
    });
  }

  getPercentWidth(value: number): string {
    const total = this.stats.totalCustomers || 1;
    const percentage = (value / total) * 100;
    return `${percentage}%`;
  }

  printReport() {
    window.print();
  }
}
export { DashboardService };
