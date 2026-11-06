import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { LoginResponse, CustomerRegistrationResponse } from '../models/user.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiBaseUrl}/api/auth`;
  
  // State representation using Angular Signals
  currentUserSignal = signal<{ username: string; role: string } | null>(null);
  
  isAuthenticated = computed(() => this.currentUserSignal() !== null);
  userRole = computed(() => this.currentUserSignal()?.role || '');

  constructor(private http: HttpClient, private router: Router) {
    this.loadSession();
  }

  private loadSession() { // Load user session from localStorage if available
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const role = localStorage.getItem('role');

    if (token && username && role) {
      this.currentUserSignal.set({ username, role });
    }
  }
 
  login(credentials: any): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(                                                                                  
      tap(res => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('username', res.username);
        localStorage.setItem('role', res.role);
        this.currentUserSignal.set({ username: res.username, role: res.role });
      })
    );
  }

  register(customerData: any): Observable<CustomerRegistrationResponse> {
    return this.http.post<CustomerRegistrationResponse>(`${this.apiUrl}/register`, customerData).pipe(
      tap(res => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('username', res.username);
        localStorage.setItem('role', res.role);
        localStorage.setItem('customerId', res.customerId.toString());
        this.currentUserSignal.set({ username: res.username, role: res.role });
      })
    );
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    localStorage.removeItem('customerId');
    this.currentUserSignal.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getCustomerId(): string | null {
    return localStorage.getItem('customerId');
  }
}
