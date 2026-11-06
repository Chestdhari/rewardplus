# RewardPlus - Full Stack Customer Loyalty & Rewards Platform

[![Frontend Deployed on Vercel](https://img.shields.io/badge/Frontend-Vercel-black?logo=vercel)](https://frontend-kohl-mu-ud7ew6yktk.vercel.app)
[![Backend Deployed on Railway](https://img.shields.io/badge/Backend-Railway-purple?logo=railway)](https://rewardplus-backend-production.up.railway.app)
[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?logo=springboot)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-20.x-red?logo=angular)](https://angular.dev)

## 🌐 Live Application Links

- **Live Web Application (Frontend)**: [https://frontend-kohl-mu-ud7ew6yktk.vercel.app](https://frontend-kohl-mu-ud7ew6yktk.vercel.app)
- **Backend API Endpoint**: `https://rewardplus-backend-production.up.railway.app`
- **GitHub Repository**: [https://github.com/Chestdhari/rewardplus](https://github.com/Chestdhari/rewardplus)

---

## 🚀 Features

- **Customer Loyalty Dashboard**: Points balance, redemption history, and available rewards.
- **Reward Management**: Browse, redeem, and manage reward items.
- **Promotions & Segments**: Targeted marketing campaigns and customer segmentation.
- **Admin Panel**: User, role, and audit log management.
- **Authentication**: JWT-based authentication and role-based access control.

---

## 🛠 Tech Stack

### Backend
- **Framework**: Spring Boot 3.5
- **Language**: Java 21
- **Security**: Spring Security & JWT
- **Database**: MySQL
- **ORM**: Spring Data JPA / Hibernate

### Frontend
- **Framework**: Angular 20
- **Styling**: SCSS & Angular Material
- **State Management**: Angular Signals & RxJS

---

## 💻 Local Development Setup

### Prerequisites
- Java 21 JDK
- Maven
- Node.js & npm
- MySQL Server

### 1. Backend Setup
```bash
cd backend
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Configure your local MySQL credentials in application.properties
./mvnw spring-boot:run
```

### 2. Frontend Setup
```bash
cd frontend
npm install
npm start
```
Open `http://localhost:4200` in your browser.
