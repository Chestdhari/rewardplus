import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User, Role, AuditLog } from '../models/user.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private usersUrl = `${environment.apiBaseUrl}/api/users`;
  private rolesUrl = `${environment.apiBaseUrl}/api/roles`;
  private logsUrl = `${environment.apiBaseUrl}/api/audit-logs`;

  constructor(private http: HttpClient) {}

  // User Management
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.usersUrl}/${id}`);
  }

  createUser(user: any): Observable<User> {
    return this.http.post<User>(this.usersUrl, user);
  }

  updateUser(id: number, user: any): Observable<User> {
    return this.http.put<User>(`${this.usersUrl}/${id}`, user);
  }

  deleteUser(id: number): Observable<string> {
    return this.http.delete(`${this.usersUrl}/${id}`, { responseType: 'text' });
  }

  // Role Management
  getAllRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(this.rolesUrl);
  }

  getRoleById(id: number): Observable<Role> {
    return this.http.get<Role>(`${this.rolesUrl}/${id}`);
  }

  createRole(role: any): Observable<Role> {
    return this.http.post<Role>(this.rolesUrl, role);
  }

  updateRole(id: number, role: any): Observable<Role> {
    return this.http.put<Role>(`${this.rolesUrl}/${id}`, role);
  }

  deleteRole(id: number): Observable<string> {
    return this.http.delete(`${this.rolesUrl}/${id}`, { responseType: 'text' });
  }

  // Audit Logs
  getAllAuditLogs(): Observable<AuditLog[]> {
    return this.http.get<AuditLog[]>(this.logsUrl);
  }

  getAuditLogById(id: number): Observable<AuditLog> {
    return this.http.get<AuditLog>(`${this.logsUrl}/${id}`);
  }
}
