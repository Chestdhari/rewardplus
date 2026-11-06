import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PromotionService } from '../../core/services/promotion.service';
import { Promotion, CustomerSegment } from '../../core/models/user.model';

@Component({
  selector: 'app-marketing-promo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container" style="max-width: 1400px; padding: 0;">
      <h1 style="font-size: 1.75rem; margin-bottom: 2rem;">Campaigns & Customer Segmentation</h1>

      <div class="grid grid-cols-2 gap-4">
        
        <!-- Left Panel: Promotions Manager -->
        <div class="rewardplus-card">
          <div class="card-header">
            <h3>Promotional Campaigns</h3>
            <span style="font-size: 0.825rem; color: var(--text-secondary);">Create points multipliers & offers</span>
          </div>

          <!-- Add/Edit Promotion Form -->
          <div style="background-color: #f8fafc; border: 1px solid var(--border-color); padding: 1rem; border-radius: var(--radius-md); margin-bottom: 1.5rem;">
            <h4 style="margin-bottom: 0.75rem;">Create New Promotion</h4>
            <form (ngSubmit)="savePromotion()">
              <div class="form-group">
                <label>Promotion Name *</label>
                <input type="text" [(ngModel)]="newPromo.promotionName" name="pName" placeholder="e.g. Double Points Weekend" required />
              </div>
              
              <div class="form-group">
                <label>Description</label>
                <input type="text" [(ngModel)]="newPromo.description" name="pDesc" placeholder="e.g. Earn 2x points on electronics" />
              </div>

              <div class="grid grid-cols-2 gap-2">
                <div class="form-group">
                  <label>Type *</label>
                  <select [(ngModel)]="newPromo.promotionType" name="pType">
                    <option value="BONUS_POINTS">Bonus Points</option>
                    <option value="DISCOUNT">Discount</option>
                    <option value="CASHBACK">Cashback</option>
                  </select>
                </div>
                
                <div class="form-group">
                  <label>Multiplier Factor *</label>
                  <input type="number" [(ngModel)]="newPromo.bonusMultiplier" name="pMult" min="1" step="0.5" />
                </div>
              </div>

              <div class="grid grid-cols-2 gap-2">
                <div class="form-group">
                  <label>Start Date *</label>
                  <input type="date" [(ngModel)]="newPromo.startDate" name="pStart" />
                </div>
                
                <div class="form-group">
                  <label>End Date *</label>
                  <input type="date" [(ngModel)]="newPromo.endDate" name="pEnd" />
                </div>
              </div>

              <div class="d-flex justify-between align-center" style="margin-top: 1rem;">
                <label class="d-flex align-center gap-2" style="font-size: 0.875rem; font-weight: 500;">
                  <input type="checkbox" [(ngModel)]="newPromo.active" name="pActive" style="width: auto;" /> Active Campaign
                </label>
                <button type="submit" class="btn-primary">
                  <span class="material-icons">save</span>
                  Save Promotion
                </button>
              </div>
            </form>
          </div>

          <!-- Promotions List Table -->
          <div style="overflow-x: auto;">
            <table class="enterprise-table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Multiplier</th>
                  <th>Validity</th>
                  <th>Status</th>
                  <th style="width: 40px;"></th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let promo of promotions">
                  <td>
                    <strong>{{ promo.promotionName }}</strong>
                    <p style="font-size: 0.75rem; color: var(--text-secondary);">{{ promo.description }}</p>
                  </td>
                  <td>{{ promo.bonusMultiplier }}x</td>
                  <td style="font-size: 0.75rem;">
                    {{ promo.startDate | date:'shortDate' }} - {{ promo.endDate | date:'shortDate' }}
                  </td>
                  <td>
                    <span class="badge" [ngClass]="promo.active ? 'badge-success' : 'badge-danger'">
                      {{ promo.active ? 'Active' : 'Inactive' }}
                    </span>
                  </td>
                  <td>
                    <button (click)="deletePromo(promo.id)" style="border: none; background: none; color: var(--danger-color); cursor: pointer;">
                      <span class="material-icons" style="font-size: 1.125rem;">delete</span>
                    </button>
                  </td>
                </tr>
                <tr *ngIf="promotions.length === 0">
                  <td colspan="5" style="text-align: center; color: var(--text-muted); padding: 2rem;">
                    No promotions configured.
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Right Panel: Customer Segments -->
        <div class="rewardplus-card">
          <div class="card-header">
            <h3>Customer Target Segments</h3>
            <span style="font-size: 0.825rem; color: var(--text-secondary);">Manage user groups for targeted rewards</span>
          </div>

          <!-- Add/Edit Segment Form -->
          <div style="background-color: #f8fafc; border: 1px solid var(--border-color); padding: 1rem; border-radius: var(--radius-md); margin-bottom: 1.5rem;">
            <h4 style="margin-bottom: 0.75rem;">Create New Segment</h4>
            <form (ngSubmit)="saveSegment()">
              <div class="form-group">
                <label>Segment Name *</label>
                <input type="text" [(ngModel)]="newSegment.segmentName" name="sName" placeholder="e.g. High Value - Gold Tier" required />
              </div>
              
              <div class="form-group">
                <label>Description</label>
                <input type="text" [(ngModel)]="newSegment.description" name="sDesc" placeholder="e.g. Customers with > 5000 lifetime points" />
              </div>

              <div class="d-flex justify-between align-center" style="margin-top: 1rem;">
                <label class="d-flex align-center gap-2" style="font-size: 0.875rem; font-weight: 500;">
                  <input type="checkbox" [(ngModel)]="newSegment.active" name="sActive" style="width: auto;" /> Active Segment
                </label>
                <button type="submit" class="btn-primary">
                  <span class="material-icons">add_box</span>
                  Add Segment
                </button>
              </div>
            </form>
          </div>

          <!-- Segments List Table -->
          <div style="overflow-x: auto;">
            <table class="enterprise-table">
              <thead>
                <tr>
                  <th>Segment Name</th>
                  <th>Description</th>
                  <th>Status</th>
                  <th style="width: 40px;"></th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let seg of segments">
                  <td><strong>{{ seg.segmentName }}</strong></td>
                  <td>{{ seg.description }}</td>
                  <td>
                    <span class="badge" [ngClass]="seg.active ? 'badge-success' : 'badge-danger'">
                      {{ seg.active ? 'Active' : 'Inactive' }}
                    </span>
                  </td>
                  <td>
                    <button (click)="deleteSegment(seg.id)" style="border: none; background: none; color: var(--danger-color); cursor: pointer;">
                      <span class="material-icons" style="font-size: 1.125rem;">delete</span>
                    </button>
                  </td>
                </tr>
                <tr *ngIf="segments.length === 0">
                  <td colspan="4" style="text-align: center; color: var(--text-muted); padding: 2rem;">
                    No customer segments configured.
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

      </div>
    </div>
  `
})
export class MarketingPromoComponent implements OnInit {
  promotions: Promotion[] = [];
  segments: CustomerSegment[] = [];

  newPromo = {
    promotionName: '',
    description: '',
    promotionType: 'BONUS_POINTS',
    bonusMultiplier: 2,
    startDate: new Date().toISOString().split('T')[0],
    endDate: new Date(Date.now() + 30 * 24 * 60 * 60 * 1000).toISOString().split('T')[0],
    active: true
  };

  newSegment = {
    segmentName: '',
    description: '',
    active: true
  };

  constructor(private promotionService: PromotionService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.promotionService.getAllPromotions().subscribe({
      next: (promos) => this.promotions = promos,
      error: (err) => console.error('Error fetching promotions', err)
    });

    this.promotionService.getAllSegments().subscribe({
      next: (segs) => this.segments = segs,
      error: (err) => console.error('Error fetching segments', err)
    });
  }

  savePromotion() {
    if (!this.newPromo.promotionName) return;

    this.promotionService.createPromotion(this.newPromo).subscribe({
      next: () => {
        this.loadData();
        this.newPromo = {
          promotionName: '',
          description: '',
          promotionType: 'BONUS_POINTS',
          bonusMultiplier: 2,
          startDate: new Date().toISOString().split('T')[0],
          endDate: new Date(Date.now() + 30 * 24 * 60 * 60 * 1000).toISOString().split('T')[0],
          active: true
        };
      },
      error: (err) => alert(err.error?.message || 'Failed to save promotion')
    });
  }

  saveSegment() {
    if (!this.newSegment.segmentName) return;

    this.promotionService.createSegment(this.newSegment).subscribe({
      next: () => {
        this.loadData();
        this.newSegment = {
          segmentName: '',
          description: '',
          active: true
        };
      },
      error: (err) => alert(err.error?.message || 'Failed to save segment')
    });
  }

  deletePromo(id: number) {
    if (confirm('Are you sure you want to delete this promotion?')) {
      this.promotionService.deletePromotion(id).subscribe({
        next: () => this.loadData(),
        error: (err) => alert('Failed to delete promotion.')
      });
    }
  }

  deleteSegment(id: number) {
    if (confirm('Are you sure you want to delete this customer segment?')) {
      this.promotionService.deleteSegment(id).subscribe({
        next: () => this.loadData(),
        error: (err) => alert('Failed to delete segment.')
      });
    }
  }
}
