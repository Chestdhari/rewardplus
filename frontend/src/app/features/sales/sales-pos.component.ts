import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../core/services/customer.service';
import { TransactionService } from '../../core/services/transaction.service';
import { RewardService } from '../../core/services/reward.service';
import { Customer, Reward, LoyaltyAccount } from '../../core/models/user.model';

@Component({
  selector: 'app-sales-pos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container" style="max-width: 1400px; padding: 0;">
      <h1 style="font-size: 1.75rem; margin-bottom: 2rem;">POS Checkout Simulator</h1>

      <div class="grid grid-cols-3 gap-4">
        
        <!-- Left Panel (2 Columns): POS Basket & Customer Search -->
        <div style="grid-column: span 2; display: flex; flex-direction: column; gap: 1.5rem;">
          
          <!-- Customer Search Card -->
          <div class="rewardplus-card">
            <div class="card-header">
              <h3>1. Link Customer Account</h3>
            </div>
            
            <div class="d-flex gap-3 align-center" style="margin-top: 1rem;">
              <div class="form-group" style="margin: 0; flex: 1;">
                <input
                  type="text"
                  [(ngModel)]="searchQuery"
                  placeholder="Search by customer phone number (e.g. 1234567890) or code..."
                  (keyup.enter)="searchCustomer()"
                />
              </div>
              <button (click)="searchCustomer()" class="btn-primary">
                <span class="material-icons">search</span>
                Search
              </button>
              <button *ngIf="selectedCustomer" (click)="clearCustomer()" class="btn-primary" style="background-color: #64748b;">
                Clear
              </button>
            </div>

            <!-- Error/Success search messages -->
            <p *ngIf="searchError" style="color: var(--danger-color); font-size: 0.875rem; margin-top: 0.5rem; font-weight: 500;">
              {{ searchError }}
            </p>

            <!-- Linked Customer Preview -->
            <div *ngIf="selectedCustomer" style="margin-top: 1.5rem; padding: 1rem; background-color: #f8fafc; border: 1px solid var(--border-color); border-radius: var(--radius-md);">
              <div class="d-flex justify-between align-center" style="margin-bottom: 0.5rem;">
                <h4 style="font-size: 1.125rem;">{{ selectedCustomer.firstName }} {{ selectedCustomer.lastName }}</h4>
                <span class="badge" [ngClass]="selectedCustomer.membershipLevel.toLowerCase()">
                  {{ selectedCustomer.membershipLevel }} Level
                </span>
              </div>
              <div class="grid grid-cols-2 gap-2" style="font-size: 0.875rem; color: var(--text-secondary);">
                <p>Customer Code: <strong>{{ selectedCustomer.customerCode }}</strong></p>
                <p>Phone: <strong>{{ selectedCustomer.phone }}</strong></p>
                <p>Current Points: <strong style="color: var(--primary-color);">{{ customerPoints }} pts</strong></p>
                <p>Enrollment: <strong>Loyalty Account Linked</strong></p>
              </div>
            </div>
          </div>

          <!-- Checkout Basket Card -->
          <div class="rewardplus-card" style="flex: 1;">
            <div class="card-header">
              <h3>2. Shopping Cart Basket</h3>
            </div>

            <!-- Add Basket Item Form -->
            <div class="grid grid-cols-4 gap-2 align-center" style="margin-top: 1rem; padding-bottom: 1rem; border-bottom: 1px solid var(--border-color);">
              <div class="form-group" style="margin: 0; grid-column: span 2;">
                <label style="font-size: 0.75rem;">Product Name</label>
                <input type="text" [(ngModel)]="newItem.productName" placeholder="e.g. Wireless Headset" />
              </div>
              
              <div class="form-group" style="margin: 0;">
                <label style="font-size: 0.75rem;">Quantity</label>
                <input type="number" [(ngModel)]="newItem.quantity" min="1" />
              </div>

              <div class="form-group" style="margin: 0;">
                <label style="font-size: 0.75rem;">Unit Price ($)</label>
                <input type="number" [(ngModel)]="newItem.unitPrice" min="0" />
              </div>
            </div>
            
            <div style="text-align: right; margin-top: 0.75rem; margin-bottom: 1rem;">
              <button (click)="addItemToBasket()" class="btn-primary" style="padding: 0.4rem 1rem; font-size: 0.825rem;">
                <span class="material-icons">add_shopping_cart</span>
                Add Item
              </button>
            </div>

            <!-- Basket Table -->
            <table class="enterprise-table" style="margin-top: 0.5rem;">
              <thead>
                <tr>
                  <th>Product</th>
                  <th>Quantity</th>
                  <th>Unit Price</th>
                  <th>Total</th>
                  <th style="width: 50px;"></th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let item of basket; let i = index">
                  <td>{{ item.productName }}</td>
                  <td>{{ item.quantity }}</td>
                  <td>\${{ item.unitPrice | number:'1.2-2' }}</td>
                  <td>\${{ (item.quantity * item.unitPrice) | number:'1.2-2' }}</td>
                  <td>
                    <button (click)="removeItemFromBasket(i)" style="border: none; background: none; color: var(--danger-color); cursor: pointer;">
                      <span class="material-icons" style="font-size: 1.125rem;">delete</span>
                    </button>
                  </td>
                </tr>
                <tr *ngIf="basket.length === 0">
                  <td colspan="5" style="text-align: center; color: var(--text-muted); padding: 2rem;">
                    Basket is empty. Add products above.
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Right Panel (1 Column): Checkout Totalizer & Reward Redemptions -->
        <div class="flex-column gap-3">
          
          <!-- Summary Receipt Card -->
          <div class="rewardplus-card" style="margin-bottom: 1.5rem;">
            <div class="card-header">
              <h3>Invoice Summary</h3>
            </div>
            
            <div style="margin-top: 1rem; display: flex; flex-direction: column; gap: 0.75rem; font-size: 0.875rem;">
              <div class="d-flex justify-between">
                <span>Subtotal:</span>
                <strong>\${{ basketSubtotal | number:'1.2-2' }}</strong>
              </div>
              
              <div class="d-flex justify-between">
                <span>Estimated Points Earned:</span>
                <strong style="color: var(--success-color);">+{{ estimatedPoints }} pts</strong>
              </div>

              <div style="border-top: 1px solid var(--border-color); padding-top: 0.75rem; margin-top: 0.5rem;" class="d-flex justify-between">
                <span style="font-size: 1rem; font-weight: 600;">Total Amount:</span>
                <strong style="font-size: 1.25rem; color: var(--primary-color);">\${{ basketSubtotal | number:'1.2-2' }}</strong>
              </div>
            </div>

            <!-- Messages -->
            <div *ngIf="checkoutSuccess" class="alert-banner success" style="margin-top: 1rem; margin-bottom: 0;">
              <span class="material-icons">check_circle</span>
              <div class="alert-content">{{ checkoutSuccess }}</div>
            </div>
            <div *ngIf="checkoutError" class="alert-banner error" style="margin-top: 1rem; margin-bottom: 0;">
              <span class="material-icons">error</span>
              <div class="alert-content">{{ checkoutError }}</div>
            </div>

            <button 
              (click)="completeCheckout()" 
              class="btn-primary" 
              style="width: 100%; justify-content: center; margin-top: 1.5rem;"
              [disabled]="basket.length === 0 || !selectedCustomer || isProcessing"
            >
              Complete POS Checkout
            </button>
          </div>

          <!-- Checkout Redemptions Panel -->
          <div *ngIf="selectedCustomer" class="rewardplus-card">
            <div class="card-header">
              <h3>Apply Reward Redemption</h3>
            </div>

            <!-- Redemption messages -->
            <div *ngIf="redeemSuccess" class="alert-banner success" style="margin-top: 0.5rem; padding: 0.5rem;">
              <div class="alert-content" style="font-size: 0.75rem;">{{ redeemSuccess }}</div>
            </div>

            <p style="font-size: 0.75rem; color: var(--text-secondary); margin-bottom: 0.75rem;">
              Deduct points immediately to award client with physical rewards during check-out:
            </p>

            <div style="display: flex; flex-direction: column; gap: 0.5rem; max-height: 250px; overflow-y: auto;">
              <div *ngFor="let reward of rewards" class="d-flex justify-between align-center" style="padding: 0.5rem; border: 1px solid var(--border-color); border-radius: 4px; font-size: 0.825rem;">
                <div>
                  <strong>{{ reward.rewardName }}</strong>
                  <p style="font-size: 0.675rem; color: var(--text-secondary);">Cost: {{ reward.pointsRequired }} pts (Qty: {{ reward.stockQuantity }})</p>
                </div>
                <button
                  (click)="redeemRewardAtCheckout(reward)"
                  class="btn-primary"
                  style="padding: 0.25rem 0.5rem; font-size: 0.675rem;"
                  [disabled]="reward.stockQuantity <= 0 || customerPoints < reward.pointsRequired || isRedeeming"
                >
                  Apply
                </button>
              </div>
              <div *ngIf="rewards.length === 0" style="text-align: center; color: var(--text-muted); font-size: 0.75rem; padding: 1rem;">
                No active rewards in catalog.
              </div>
            </div>
          </div>
          
        </div>
      </div>
    </div>
  `
})
export class SalesPosComponent implements OnInit {
  searchQuery = '';
  searchError = '';
  
  selectedCustomer: Customer | null = null;
  customerPoints = 0;
  loyaltyAccount: LoyaltyAccount | null = null;

  basket: { productName: string; quantity: number; unitPrice: number }[] = [];
  newItem = { productName: '', quantity: 1, unitPrice: 0 };
  
  rewards: Reward[] = [];

  isProcessing = false;
  checkoutSuccess = '';
  checkoutError = '';

  isRedeeming = false;
  redeemSuccess = '';

  constructor(
    private customerService: CustomerService,
    private transactionService: TransactionService,
    private rewardService: RewardService
  ) {}

  ngOnInit() {
    this.loadRewardsCatalog();
  }

  loadRewardsCatalog() {
    this.rewardService.getAllRewards().subscribe({
      next: (rewards) => {
        this.rewards = rewards.filter(r => r.active);
      },
      error: (err) => console.error('Error loading rewards', err)
    });
  }

  searchCustomer() {
    if (!this.searchQuery) return;

    this.searchError = '';
    this.selectedCustomer = null;

    this.customerService.getAllCustomers().subscribe({
      next: (customers) => {
        const found = customers.find(
          c => c.phone === this.searchQuery || c.customerCode === this.searchQuery
        );

        if (found) {
          this.selectedCustomer = found;
          // Load points details via loyalty accounts
          this.transactionService.getAllLoyaltyAccounts().subscribe(accounts => {
            const acc = accounts.find(a => a.customerId === found.id);
            if (acc) {
              this.loyaltyAccount = acc;
              this.customerPoints = acc.availablePoints;
            } else {
              this.customerPoints = found.currentPoints;
            }
          });
        } else {
          this.searchError = 'No customer profile found with that phone or customer code.';
        }
      },
      error: (err) => {
        this.searchError = 'Error connecting to database. Please check backend connection.';
      }
    });
  }

  clearCustomer() {
    this.selectedCustomer = null;
    this.loyaltyAccount = null;
    this.customerPoints = 0;
    this.searchQuery = '';
    this.searchError = '';
  }

  addItemToBasket() {
    if (!this.newItem.productName || this.newItem.quantity <= 0 || this.newItem.unitPrice < 0) return;

    this.basket.push({ ...this.newItem });
    this.newItem = { productName: '', quantity: 1, unitPrice: 0 };
  }

  removeItemFromBasket(index: number) {
    this.basket.splice(index, 1);
  }

  get basketSubtotal(): number {
    return this.basket.reduce((sum, item) => sum + (item.quantity * item.unitPrice), 0);
  }

  get estimatedPoints(): number {
    // 1 point per $100 spent (matches backend logic)
    return Math.floor(this.basketSubtotal / 100);
  }

  redeemRewardAtCheckout(reward: Reward) {
    if (!this.selectedCustomer || this.isRedeeming) return;

    this.isRedeeming = true;
    this.redeemSuccess = '';

    const req = {
      customerId: this.selectedCustomer.id,
      rewardId: reward.id
    };

    this.rewardService.createRedemption(req).subscribe({
      next: (res) => {
        this.isRedeeming = false;
        this.customerPoints -= reward.pointsRequired;
        this.redeemSuccess = `Applied: Redeemed ${reward.rewardName} successfully!`;
        // Refresh catalog quantities
        this.loadRewardsCatalog();
        setTimeout(() => this.redeemSuccess = '', 4000);
      },
      error: (err) => {
        this.isRedeeming = false;
        alert(err.error?.message || 'Failed to redeem reward.');
      }
    });
  }

  completeCheckout() {
    if (this.basket.length === 0 || !this.selectedCustomer || this.isProcessing) return;

    this.isProcessing = true;
    this.checkoutSuccess = '';
    this.checkoutError = '';

    const request = {
      customerId: this.selectedCustomer.id,
      items: this.basket
    };

    this.transactionService.createTransaction(request).subscribe({
      next: (res) => {
        this.isProcessing = false;
        this.checkoutSuccess = `Checkout Completed! Invoice #${res.transactionNumber} created. Points awarded automatically.`;
        this.basket = []; // empty basket
        
        // Reload customer loyalty account status
        this.searchCustomer();
        setTimeout(() => this.checkoutSuccess = '', 6000);
      },
      error: (err) => {
        this.isProcessing = false;
        this.checkoutError = err.error?.message || 'Checkout execution failed.';
      }
    });
  }
}
