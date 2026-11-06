import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin, map } from 'rxjs';
import { CustomerDashboardResponse } from '../models/user.model';
import { CustomerService } from './customer.service';
import { TransactionService } from './transaction.service';
import { RewardService } from './reward.service';
import { PromotionService } from './promotion.service';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private customerDashboardUrl = `${environment.apiBaseUrl}/api/customer/dashboard`;

  constructor(
    private http: HttpClient,
    private customerService: CustomerService,
    private transactionService: TransactionService,
    private rewardService: RewardService,
    private promotionService: PromotionService
  ) {}

  // Customer self-service dashboard
  getCustomerDashboard(): Observable<CustomerDashboardResponse> {
    return this.http.get<CustomerDashboardResponse>(this.customerDashboardUrl);
  }

  // Manager executive dashboard KPIs and charts data
  getManagerDashboardData(): Observable<{
    totalCustomers: number;
    activeMembers: number;
    totalRevenue: number;
    totalPointsIssued: number;
    totalPointsRedeemed: number;
    activePromotions: number;
    loyaltyTiers: { bronze: number; silver: number; gold: number; platinum: number };
    recentTransactions: any[];
    redemptionStats: { name: string; count: number }[];
  }> {
    return forkJoin({
      customers: this.customerService.getAllCustomers(),
      transactions: this.transactionService.getAllTransactions(),
      loyaltyAccounts: this.transactionService.getAllLoyaltyAccounts(),
      redemptions: this.rewardService.getAllRedemptions(),
      promotions: this.promotionService.getAllPromotions()
    }).pipe(
      map(data => {
        // Total customers
        const totalCustomers = data.customers.length;
        const activeMembers = data.customers.filter(c => c.active).length;

        // Total revenue
        const totalRevenue = data.transactions.reduce((sum, tx) => sum + tx.totalAmount, 0);

        // Loyalty tiers distribution and points calculations
        let totalPointsIssued = 0;
        let totalPointsRedeemed = 0;
        const loyaltyTiers = { bronze: 0, silver: 0, gold: 0, platinum: 0 };

        data.loyaltyAccounts.forEach(acc => {
          totalPointsIssued += acc.lifetimePoints;
          // Available points represents the net points, so redeemed points = lifetimePoints - availablePoints
          totalPointsRedeemed += (acc.lifetimePoints - acc.availablePoints);
          
          const tier = acc.membershipLevel.toLowerCase();
          if (tier === 'bronze') loyaltyTiers.bronze++;
          else if (tier === 'silver') loyaltyTiers.silver++;
          else if (tier === 'gold') loyaltyTiers.gold++;
          else if (tier === 'platinum') loyaltyTiers.platinum++;
        });

        // Active promotions count
        const activePromotions = data.promotions.filter(p => p.active).length;

        // Redemption counts by reward
        const redemptionsMap = new Map<string, number>();
        data.redemptions.forEach(red => {
          const count = redemptionsMap.get(red.rewardName) || 0;
          redemptionsMap.set(red.rewardName, count + 1);
        });

        const redemptionStats = Array.from(redemptionsMap.entries()).map(([name, count]) => ({
          name,
          count
        })).sort((a, b) => b.count - a.count).slice(0, 5);

        // Sort and get recent transactions
        const recentTransactions = [...data.transactions]
          .sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
          .slice(0, 5);

        return {
          totalCustomers,
          activeMembers,
          totalRevenue,
          totalPointsIssued,
          totalPointsRedeemed,
          activePromotions,
          loyaltyTiers,
          recentTransactions,
          redemptionStats
        };
      })
    );
  }
}
