import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reward, RewardCategory, Redemption } from '../models/user.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RewardService {
  private rewardUrl = `${environment.apiBaseUrl}/api/rewards`;
  private categoryUrl = `${environment.apiBaseUrl}/api/reward-categories`;
  private redemptionUrl = `${environment.apiBaseUrl}/api/redemptions`;
  
  private customerRewardUrl = `${environment.apiBaseUrl}/api/customer/rewards`;
  private customerRedemptionUrl = `${environment.apiBaseUrl}/api/customer/redemptions`;

  constructor(private http: HttpClient) {}

  // Reward Management (Staff)
  getAllRewards(): Observable<Reward[]> {
    return this.http.get<Reward[]>(this.rewardUrl);
  }

  getRewardById(id: number): Observable<Reward> {
    return this.http.get<Reward>(`${this.rewardUrl}/${id}`);
  }

  createReward(reward: any): Observable<Reward> {
    return this.http.post<Reward>(this.rewardUrl, reward);
  }

  updateReward(id: number, reward: any): Observable<Reward> {
    return this.http.put<Reward>(`${this.rewardUrl}/${id}`, reward);
  }

  deleteReward(id: number): Observable<void> {
    return this.http.delete<void>(`${this.rewardUrl}/${id}`);
  }

  // Reward Categories (Staff)
  getAllCategories(): Observable<RewardCategory[]> {
    return this.http.get<RewardCategory[]>(this.categoryUrl);
  }

  getCategoryById(id: number): Observable<RewardCategory> {
    return this.http.get<RewardCategory>(`${this.categoryUrl}/${id}`);
  }

  createCategory(category: any): Observable<RewardCategory> {
    return this.http.post<RewardCategory>(this.categoryUrl, category);
  }

  updateCategory(id: number, category: any): Observable<RewardCategory> {
    return this.http.put<RewardCategory>(`${this.categoryUrl}/${id}`, category);
  }

  deleteCategory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.categoryUrl}/${id}`);
  }

  // Redemption Management (Staff POS / Admin)
  getAllRedemptions(): Observable<Redemption[]> {
    return this.http.get<Redemption[]>(this.redemptionUrl);
  }

  createRedemption(redemptionRequest: { customerId: number; rewardId: number }): Observable<Redemption> {
    return this.http.post<Redemption>(this.redemptionUrl, redemptionRequest);
  }

  // Customer Catalog & Redeeming
  getActiveCustomerRewards(): Observable<Reward[]> {
    return this.http.get<Reward[]>(this.customerRewardUrl);
  }

  redeemRewardForCustomer(rewardId: number): Observable<Redemption> {
    return this.http.post<Redemption>(this.customerRedemptionUrl, { rewardId });
  }

  getMyRedemptions(): Observable<Redemption[]> {
    return this.http.get<Redemption[]>(this.customerRedemptionUrl);
  }
}
