# MarineLink Portal – Backend (Spring Boot)

MarineLink Portal is a role-based marine marketplace system built with **Spring Boot**, **PostgreSQL**, and **JWT Authentication**. It supports **Fishers**, **Buyers**, **Admins**, and **Regulators**, each with different dashboards and permissions.

---

## Features

### User Management
- Role-based access (Admin, Fisher, Buyer, Regulator)
- JWT authentication & authorization
- CRUD operations for users (Admin only)

### Listings
- Fishers create listings (species, location, weight, price/kg)
- Admin can view all listings
- Buyers can browse listings
- Status flow: `AVAILABLE → PENDING → SOLD`

### Transactions
- Buyers purchase listings
- Fisher & Admin can update transaction status
- Automatic total price calculation (weight × price/kg)
- Monthly analytics for revenue & orders

### Dashboards
- **Admin Dashboard** – users, listings, transactions, revenue, monthly charts
- **Fisher Dashboard** – listings, transactions, revenue, pending/completed orders
- **Buyer Dashboard** – spending, orders, monthly charts
- **Regulator Dashboard** – system-wide analytics

---

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Security + JWT
- Hibernate / JPA
- PostgreSQL
- Docker & Docker Compose

---

## Project Structure

```
src/main/java/ac/tz/suza/marinelink_portal/
│
├── controllers/        → REST API endpoints
├── services/            → Business logic
├── repositories/        → JPA repositories
├── models/entities/     → Database entities
├── dto/                 → Request/response DTOs
└── security/            → JWT filters & config
```

---

## Running the Project

### 1. Build the JAR
```bash
mvn clean package
```

### 2. Start with Docker
```bash
docker-compose up --build
```

Backend runs at:
```
http://localhost:8080
```

---

## Environment Variables

Set these in your `application.properties` or Docker env:

```properties
spring.datasource.url=jdbc:postgresql://marinelink-db:5432/marinelink
spring.datasource.username=postgres
spring.datasource.password=postgres
jwt.secret=your-secret-key
```

---

## API Endpoints (Summary)

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Authenticate a user |
| POST | `/api/auth/register` | Register a new user |

### Users (Admin Only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | List all users |
| DELETE | `/api/users/{id}` | Delete a user |

### Listings
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/listings` | Create a listing |
| GET | `/api/listings` | Get all listings |
| GET | `/api/listings/fisher/{id}` | Get listings by fisher |

### Transactions
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/transactions` | Create a transaction |
| GET | `/api/transactions` | Get all transactions |
| PUT | `/api/transactions/{id}/status` | Update transaction status |

### Dashboards
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/dashboard/admin` | Admin dashboard data |
| GET | `/api/dashboard/fisher/{id}` | Fisher dashboard data |
| GET | `/api/dashboard/buyer/{id}` | Buyer dashboard data |
| GET | `/api/dashboard/regulator` | Regulator dashboard data |

---

## Docker Services

### Backend
- Builds from `Dockerfile`
- Runs the Spring Boot JAR

### Database
- PostgreSQL 15
- Auto-creates database on first run
