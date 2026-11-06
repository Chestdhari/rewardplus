import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    return true;
  }

  // Not authenticated, redirect to login
  router.navigate(['/login']);
  return false;
};

export const roleGuard = (allowedRoles: string[]): CanActivateFn => {
  return (route, state) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (!authService.isAuthenticated()) {
      router.navigate(['/login']);
      return false;
    }

    const currentRole = authService.userRole();
    if (allowedRoles.includes(currentRole)) {
      return true;
    }

    // Role not authorized, redirect to default view based on role or home
    if (currentRole === 'CUSTOMER') {
      router.navigate(['/customer/dashboard']);
    } else if (currentRole === 'SALES') {
      router.navigate(['/sales/pos']);
    } else if (currentRole === 'MARKETING') {
      router.navigate(['/marketing/promotions']);
    } else if (currentRole === 'MANAGER') {
      router.navigate(['/manager/dashboard']);
    } else if (currentRole === 'ADMIN') {
      router.navigate(['/admin/users']);
    } else {
      router.navigate(['/login']);
    }
    return false;
  };
};
