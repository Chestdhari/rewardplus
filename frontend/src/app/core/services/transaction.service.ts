import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PurchaseTransaction, LoyaltyAccount, LoyaltyTransaction } from '../models/user.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private baseTxUrl = `${environment.apiBaseUrl}/api/transactions`;
  private baseLoyaltyUrl = `${environment.apiBaseUrl}/api/loyalty-accounts`;
  private customerLoyaltyUrl = `${environment.apiBaseUrl}/api/customer/loyalty`;
  private customerTxUrl = `${environment.apiBaseUrl}/api/customer/transactions`;

  constructor(private http: HttpClient) {}

  // Purchase/POS Transactions
  getAllTransactions(): Observable<PurchaseTransaction[]> {
    return this.http.get<PurchaseTransaction[]>(this.baseTxUrl);
  }

  getTransactionById(id: number): Observable<PurchaseTransaction> {
    return this.http.get<PurchaseTransaction>(`${this.baseTxUrl}/${id}`);
  }

  createTransaction(txnData: any): Observable<PurchaseTransaction> {
    return this.http.post<PurchaseTransaction>(this.baseTxUrl, txnData);
  }

  // Loyalty Accounts Management (Admin/Sales)
  getAllLoyaltyAccounts(): Observable<LoyaltyAccount[]> {
    return this.http.get<LoyaltyAccount[]>(this.baseLoyaltyUrl);
  }

  getLoyaltyAccountById(id: number): Observable<LoyaltyAccount> {
    return this.http.get<LoyaltyAccount>(`${this.baseLoyaltyUrl}/${id}`);
  }

  earnPoints(accountId: number, data: { points: number; description: string; referenceId?: string }): Observable<LoyaltyAccount> {
    return this.http.post<LoyaltyAccount>(`${this.baseLoyaltyUrl}/${accountId}/earn`, data);
  }

  redeemPoints(accountId: number, data: { points: number; description: string; referenceId?: string }): Observable<LoyaltyAccount> {
    return this.http.post<LoyaltyAccount>(`${this.baseLoyaltyUrl}/${accountId}/redeem`, data);
  }

  // Customer Self-service APIs
  getMyLoyaltyAccount(): Observable<LoyaltyAccount> {
    return this.http.get<LoyaltyAccount>(this.customerLoyaltyUrl);
  }

  getMyTransactions(): Observable<LoyaltyTransaction[]> {
    return this.http.get<LoyaltyTransaction[]>(this.customerTxUrl);
  }
}
