import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login.component';
import { RegisterComponent } from './features/auth/register.component';
import { LayoutComponent } from './shared/components/layout/layout.component';
import { CustomerDashboardComponent } from './features/customer/customer-dashboard.component';
import { SalesPosComponent } from './features/sales/sales-pos.component';
import { MarketingPromoComponent } from './features/marketing/marketing-promo.component';
import { ManagerDashboardComponent } from './features/manager/manager-dashboard.component';
import { AdminPanelComponent } from './features/admin/admin-panel.component';
import { authGuard, roleGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  // Public auth routes
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Shell Layout enclosing guarded dashboard pages
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'customer/dashboard',
        component: CustomerDashboardComponent,
        canActivate: [roleGuard(['CUSTOMER', 'ADMIN'])]
      },
      {
        path: 'sales/pos',
        component: SalesPosComponent,
        canActivate: [roleGuard(['SALES', 'ADMIN', 'MANAGER'])]
      },
      {
        path: 'marketing/promotions',
        component: MarketingPromoComponent,
        canActivate: [roleGuard(['MARKETING', 'ADMIN', 'MANAGER'])]
      },
      {
        path: 'manager/dashboard',
        component: ManagerDashboardComponent,
        canActivate: [roleGuard(['MANAGER', 'ADMIN'])]
      },
      {
        path: 'admin/users',
        component: AdminPanelComponent,
        canActivate: [roleGuard(['ADMIN'])]
      },
      // Default fallback inside shell layout based on role redirection
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'login'
      }
    ]
  },
  
  // Catch-all fallback
  { path: '**', redirectTo: 'login' }
];
