import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Promotion, CustomerSegment } from '../models/user.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PromotionService {
  private promoUrl = `${environment.apiBaseUrl}/api/promotions`;
  private segmentUrl = `${environment.apiBaseUrl}/api/customer-segments`;

  constructor(private http: HttpClient) {}

  // Promotions
  getAllPromotions(): Observable<Promotion[]> {
    return this.http.get<Promotion[]>(this.promoUrl);
  }

  getPromotionById(id: number): Observable<Promotion> {
    return this.http.get<Promotion>(`${this.promoUrl}/${id}`);
  }

  createPromotion(promo: any): Observable<Promotion> {
    return this.http.post<Promotion>(this.promoUrl, promo);
  }

  updatePromotion(id: number, promo: any): Observable<Promotion> {
    return this.http.put<Promotion>(`${this.promoUrl}/${id}`, promo);
  }

  deletePromotion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.promoUrl}/${id}`);
  }

  // Customer Segments
  getAllSegments(): Observable<CustomerSegment[]> {
    return this.http.get<CustomerSegment[]>(this.segmentUrl);
  }

  getSegmentById(id: number): Observable<CustomerSegment> {
    return this.http.get<CustomerSegment>(`${this.segmentUrl}/${id}`);
  }

  createSegment(segment: any): Observable<CustomerSegment> {
    return this.http.post<CustomerSegment>(this.segmentUrl, segment);
  }

  updateSegment(id: number, segment: any): Observable<CustomerSegment> {
    return this.http.put<CustomerSegment>(`${this.segmentUrl}/${id}`, segment);
  }

  deleteSegment(id: number): Observable<void> {
    return this.http.delete<void>(`${this.segmentUrl}/${id}`);
  }
}
