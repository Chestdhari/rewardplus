import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../core/services/dashboard.service';
import { RewardService } from '../../core/services/reward.service';
import { PromotionService } from '../../core/services/promotion.service';
import { CustomerDashboardResponse, Reward, Promotion, Redemption } from '../../core/models/user.model';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container" style="max-width: 1400px; padding: 0;">
      <!-- Title Header -->
      <div class="d-flex justify-between align-center" style="margin-bottom: 2rem;">
        <div>
          <h1 style="font-size: 1.75rem; margin-bottom: 0.25rem;">Welcome back, {{ dashboardData?.customerName }}!</h1>
          <p>Customer Code: <strong style="color: var(--primary-color);">{{ dashboardData?.customerCode }}</strong></p>
        </div>
        <div>
          <span class="badge" [ngClass]="dashboardData?.membershipLevel?.toLowerCase() || 'bronze'" style="font-size: 0.875rem; padding: 0.5rem 1rem;">
            {{ dashboardData?.membershipLevel }} member
          </span>
        </div>
      </div>

      <!-- KPI Summary Cards Grid -->
      <div class="grid grid-cols-4 gap-3" style="margin-bottom: 2.5rem;">
        <div class="rewardplus-card">
          <div class="d-flex justify-between align-center" style="margin-bottom: 0.5rem;">
            <p style="font-size: 0.875rem; font-weight: 500;">Available Points</p>
            <span class="material-icons" style="color: var(--primary-color);">stars</span>
          </div>
          <h2 style="font-size: 2rem; font-weight: 700; color: var(--primary-color);">
            {{ dashboardData?.availablePoints || 0 }}
          </h2>
          <p style="font-size: 0.75rem; color: var(--text-secondary); margin-top: 0.25rem;">Ready to redeem</p>
        </div>

        <div class="rewardplus-card">
          <div class="d-flex justify-between align-center" style="margin-bottom: 0.5rem;">
            <p style="font-size: 0.875rem; font-weight: 500;">Current Level</p>
            <span class="material-icons" style="color: #64748b;">emoji_events</span>
          </div>
          <h2 style="font-size: 2rem; font-weight: 700; text-transform: capitalize;">
            {{ dashboardData?.membershipLevel }}
          </h2>
          <p style="font-size: 0.75rem; color: var(--text-secondary); margin-top: 0.25rem;">Tier status level</p>
        </div>

        <div class="rewardplus-card">
          <div class="d-flex justify-between align-center" style="margin-bottom: 0.5rem;">
            <p style="font-size: 0.875rem; font-weight: 500;">Lifetime Points</p>
            <span class="material-icons" style="color: var(--success-color);">military_tech</span>
          </div>
          <h2 style="font-size: 2rem; font-weight: 700; color: var(--success-color);">
            {{ dashboardData?.lifetimePoints || 0 }}
          </h2>
          <p style="font-size: 0.75rem; color: var(--text-secondary); margin-top: 0.25rem;">Total points accumulated</p>
        </div>

        <div class="rewardplus-card">
          <div class="d-flex justify-between align-center" style="margin-bottom: 0.5rem;">
            <p style="font-size: 0.875rem; font-weight: 500;">Next Tier Progress</p>
            <span class="material-icons" style="color: var(--warning-color);">trending_up</span>
          </div>
          <ng-container *ngIf="dashboardData?.nextMembershipLevel; else maxTier">
            <h2 style="font-size: 2rem; font-weight: 700; color: var(--text-primary);">
              {{ dashboardData?.pointsToNextLevel || 0 }} <span style="font-size: 0.875rem; font-weight: 500; color: var(--text-secondary);">pts</span>
            </h2>
            <p style="font-size: 0.75rem; color: var(--text-secondary); margin-top: 0.25rem;">to reach <strong>{{ dashboardData?.nextMembershipLevel }}</strong></p>
          </ng-container>
          <ng-template #maxTier>
            <h2 style="font-size: 1.5rem; font-weight: 700; color: var(--primary-color);">Ultimate Tier</h2>
            <p style="font-size: 0.75rem; color: var(--text-secondary); margin-top: 0.25rem;">You are at the top level!</p>
          </ng-template>
        </div>
      </div>

      <!-- Main Layout Panels -->
      <div class="grid grid-cols-3 gap-4">
        
        <!-- Left 2 Columns: Rewards Catalog -->
        <div class="flex-1" style="grid-column: span 2;">
          <div class="rewardplus-card" style="min-height: 400px;">
            <div class="card-header">
              <h3>Eligible Rewards Catalog</h3>
              <span style="font-size: 0.875rem; color: var(--text-secondary);">Select and redeem active rewards</span>
            </div>

            <!-- Messages -->
            <div *ngIf="redeemSuccess" class="alert-banner success">
              <span class="material-icons">check_circle</span>
              <div class="alert-content">{{ redeemSuccess }}</div>
            </div>
            <div *ngIf="redeemError" class="alert-banner error">
              <span class="material-icons">error</span>
              <div class="alert-content">{{ redeemError }}</div>
            </div>

            <div class="grid grid-cols-2 gap-3" style="margin-top: 1rem;">
              <div *ngFor="let reward of rewards" class="rewardplus-card hoverable" style="padding: 1rem; display: flex; flex-direction: column; justify-content: space-between;">
                <div>
                  <div class="d-flex justify-between align-center" style="margin-bottom: 0.5rem;">
                    <span class="badge" style="font-size: 0.675rem; background-color: var(--primary-light); color: var(--primary-color);">
                      {{ reward.category?.categoryName || 'General' }}
                    </span>
                    <span style="font-size: 0.75rem; font-weight: 500;" [ngClass]="reward.stockQuantity > 0 ? 'badge badge-success' : 'badge badge-danger'">
                      {{ reward.stockQuantity > 0 ? reward.stockQuantity + ' in stock' : 'Out of stock' }}
                    </span>
                  </div>
                  <h4 style="font-size: 1rem; margin-bottom: 0.25rem; color: var(--text-primary);">{{ reward.rewardName }}</h4>
                  <p style="font-size: 0.825rem; color: var(--text-secondary); margin-bottom: 1rem;">{{ reward.description }}</p>
                </div>

                <div class="d-flex justify-between align-center" style="border-top: 1px solid var(--border-color); padding-top: 0.75rem; margin-top: 0.5rem;">
                  <div>
                    <span style="font-size: 1.125rem; font-weight: 700; color: var(--primary-color);">{{ reward.pointsRequired }}</span>
                    <span style="font-size: 0.75rem; color: var(--text-secondary); margin-left: 0.125rem;">pts</span>
                  </div>
                  
                  <button 
                    (click)="redeem(reward)" 
                    class="btn-primary" 
                    style="padding: 0.4rem 0.75rem; font-size: 0.75rem;" 
                    [disabled]="reward.stockQuantity <= 0 || (dashboardData?.availablePoints || 0) < reward.pointsRequired || isRedeeming"
                  >
                    Redeem
                  </button>
                </div>
              </div>
            </div>

            <div *ngIf="rewards.length === 0" style="text-align: center; padding: 3rem; color: var(--text-muted);">
              <span class="material-icons" style="font-size: 3rem; margin-bottom: 0.5rem;">card_giftcard</span>
              <p>No rewards currently available for redemption.</p>
            </div>
          </div>
        </div>

        <!-- Right 1 Column: Activity Feed (History & Promotions) -->
        <div class="flex-column gap-4">
          <!-- Recent Transactions -->
          <div class="rewardplus-card" style="margin-bottom: 1.5rem;">
            <div class="card-header">
              <h3>Point History</h3>
            </div>
            
            <div *ngIf="dashboardData?.recentTransactions?.length; else noTx" style="display: flex; flex-direction: column; gap: 0.75rem;">
              <div *ngFor="let tx of dashboardData?.recentTransactions" class="d-flex justify-between align-center" style="padding-bottom: 0.5rem; border-bottom: 1px dashed var(--border-color);">
                <div>
                  <p style="font-size: 0.875rem; font-weight: 600; color: var(--text-primary);">{{ tx.description }}</p>
                  <p style="font-size: 0.75rem; color: var(--text-muted);">{{ tx.createdAt | date:'shortDate' }}</p>
                </div>
                <div style="text-align: right;">
                  <strong [ngStyle]="{color: tx.transactionType === 'EARN' ? 'var(--success-color)' : 'var(--danger-color)'}">
                    {{ tx.transactionType === 'EARN' ? '+' : '-' }}{{ tx.points }}
                  </strong>
                  <p style="font-size: 0.75rem; color: var(--text-muted);">Bal: {{ tx.balanceAfterTransaction }}</p>
                </div>
              </div>
            </div>
            <ng-template #noTx>
              <div style="text-align: center; padding: 1.5rem; color: var(--text-muted); font-size: 0.875rem;">
                No points transactions recorded.
              </div>
            </ng-template>
          </div>

          <!-- Active Campaigns -->
          <div class="rewardplus-card">
            <div class="card-header">
              <h3>Active Promotions</h3>
            </div>
            
            <div *ngIf="promotions.length; else noPromo" style="display: flex; flex-direction: column; gap: 1rem;">
              <div *ngFor="let promo of promotions" class="alert-banner info" style="margin: 0; padding: 0.75rem;">
                <span class="material-icons">local_offer</span>
                <div class="alert-content">
                  <strong style="color: var(--text-primary);">{{ promo.promotionName }}</strong>
                  <p style="font-size: 0.75rem; color: var(--text-secondary); margin-top: 0.125rem;">
                    {{ promo.description }} - 
                    <span style="color: var(--primary-color); font-weight: 600;">
                      {{ promo.bonusMultiplier }}x Multiplier
                    </span>
                  </p>
                  <p style="font-size: 0.675rem; color: var(--text-muted); margin-top: 0.25rem;">
                    Valid until {{ promo.endDate | date:'mediumDate' }}
                  </p>
                </div>
              </div>
            </div>
            <ng-template #noPromo>
              <div style="text-align: center; padding: 1.5rem; color: var(--text-muted); font-size: 0.875rem;">
                No promotions running right now. Check back later!
              </div>
            </ng-template>
          </div>
        </div>

      </div>
    </div>
  `
})
export class CustomerDashboardComponent implements OnInit {
  dashboardData?: CustomerDashboardResponse;
  rewards: Reward[] = [];
  promotions: Promotion[] = [];
  
  isRedeeming = false;
  redeemSuccess = '';
  redeemError = '';

  constructor(
    private dashboardService: DashboardService,
    private rewardService: RewardService,
    private promotionService: PromotionService
  ) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.dashboardService.getCustomerDashboard().subscribe({
      next: (data) => {
        this.dashboardData = data;
      },
      error: (err) => console.error('Error fetching dashboard', err)
    });

    this.rewardService.getActiveCustomerRewards().subscribe({
      next: (rewards) => {
        this.rewards = rewards;
      },
      error: (err) => console.error('Error fetching rewards', err)
    });

    this.promotionService.getAllPromotions().subscribe({
      next: (promos) => {
        this.promotions = promos.filter(p => p.active);
      },
      error: (err) => console.error('Error fetching promotions', err)
    });
  }

  redeem(reward: Reward) {
    if (this.isRedeeming) return;

    this.isRedeeming = true;
    this.redeemSuccess = '';
    this.redeemError = '';

    this.rewardService.redeemRewardForCustomer(reward.id).subscribe({
      next: (res) => {
        this.isRedeeming = false;
        this.redeemSuccess = `Successfully redeemed ${reward.rewardName}! Deducted ${reward.pointsRequired} points.`;
        this.loadData(); // reload dashboards & balances
        setTimeout(() => this.redeemSuccess = '', 6000);
      },
      error: (err) => {
        this.isRedeeming = false;
        this.redeemError = err.error?.message || 'Failed to redeem reward. Please try again.';
        setTimeout(() => this.redeemError = '', 6000);
      }
    });
  }
}
