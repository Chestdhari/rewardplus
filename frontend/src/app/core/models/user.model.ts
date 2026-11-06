export interface LoginResponse {
  token: string;
  username: string;
  role: string;
}

export interface CustomerRegistrationResponse {
  token: string;
  username: string;
  role: string;
  customerId: number;
}

export interface User {
  id?: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  active: boolean;
  roles?: Role[];
}

export interface Role {
  id?: number;
  roleName: string;
  description: string;
  active: boolean;
}

export interface Customer {
  id: number;
  customerCode: string;
  firstName: string;
  lastName: string;
  phone: string;
  dateOfBirth?: string;
  address?: string;
  city?: string;
  state?: string;
  country?: string;
  pinCode?: string;
  membershipLevel: 'BRONZE' | 'SILVER' | 'GOLD' | 'PLATINUM';
  currentPoints: number;
  active: boolean;
  userId?: number;
}

export interface LoyaltyAccount {
  id: number;
  customerId: number;
  currentPoints: number;
  availablePoints: number;
  lifetimePoints: number;
  membershipLevel: 'BRONZE' | 'SILVER' | 'GOLD' | 'PLATINUM';
  active: boolean;
  createdAt?: string;
  updatedAt?: string;
}

export interface LoyaltyTransaction {
  id: number;
  transactionType: 'EARN' | 'REDEEM' | 'EXPIRY' | 'ADJUSTMENT';
  points: number;
  balanceAfterTransaction: number;
  description: string;
  referenceId?: string;
  createdAt: string;
}

export interface TransactionItem {
  id?: number;
  productName: string;
  quantity: number;
  unitPrice: number;
  totalPrice?: number;
}

export interface PurchaseTransaction {
  id: number;
  transactionNumber: string;
  customerId: number;
  totalAmount: number;
  status: 'PENDING' | 'COMPLETED' | 'CANCELLED';
  items: TransactionItem[];
  createdAt: string;
}

export interface RewardCategory {
  id: number;
  categoryName: string;
  description?: string;
  active: boolean;
}

export interface Reward {
  id: number;
  rewardName: string;
  description?: string;
  pointsRequired: number;
  stockQuantity: number;
  active: boolean;
  category?: RewardCategory;
}

export interface Redemption {
  id: number;
  customerId: number;
  rewardName: string;
  pointsRedeemed: number;
  redemptionStatus: 'COMPLETED' | 'CANCELLED' | 'PENDING';
  createdAt: string;
}

export interface CustomerSegment {
  id: number;
  segmentName: string;
  description?: string;
  active: boolean;
}

export interface Promotion {
  id: number;
  promotionName: string;
  description?: string;
  promotionType: 'BONUS_POINTS' | 'DISCOUNT' | 'CASHBACK';
  bonusMultiplier: number;
  startDate: string;
  endDate: string;
  active: boolean;
}

export interface AuditLog {
  id: number;
  username: string;
  action: string;
  entityName: string;
  entityId?: string;
  description?: string;
  createdAt: string;
}

export interface CustomerDashboardResponse {
  customerName: string;
  customerCode: string;
  membershipLevel: 'BRONZE' | 'SILVER' | 'GOLD' | 'PLATINUM';
  currentPoints: number;
  availablePoints: number;
  lifetimePoints: number;
  nextMembershipLevel?: string;
  pointsToNextLevel?: number;
  activeRewards: number;
  totalTransactions: number;
  totalRedemptions: number;
  recentTransactions: LoyaltyTransaction[];
  recentRedemptions: Redemption[];
}
