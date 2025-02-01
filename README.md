# schedule-transfer

[[_TOC_]]

---

:scroll: **START**

### 
This is an application capable of

- Schedule a Transfer;
- Retrieve Scheduled Transfers for a User;
- Cancel Scheduled Transfer for a User;

### Prerequisites

- Java JDK 17
- Maven
- Spring Boot 3.4.2
- Flyway
- H2 Database
- Swagger documentation

### Getting Started

These instructions will help you  build, run, and test  the project on your local machine.

### Build the application

Execute this command in your terminal - mvn clean install

### Run the application

Execute this command in your terminal - mvn spring-boot:run

### Test the application

Execute this command in your terminal - mvn test

### Application URLs

Swagger documentation Url - http://localhost:8010/swagger-ui/index.html

H2 database login url - http://localhost:8000/h2-console

### Example API Requests and Responses

#### 1. **Schedule a Transfer**

**POST /api/transfer/schedules**

**Request Body:**
```json
{
  "senderAccountId": "6238411689",
  "recipientAccountId": "1853614738",
  "transferAmount": 1,
  "transferDate": "2025-02-01T10:40:42.006102"
}
```
**Response Body:**

Status: 201 Created
```json
{
  "transferId": "c4afd3d9-dafa-449b-ba3a-9a3e0d7cc0bb",
  "senderAccountId": "6238411689",
  "recipientAccountId": "1853614738",
  "transferAmount": 1,
  "transferDate": "2026-02-01T10:40:42.006102",
  "status": "PENDING",
  "createdAt": "2025-02-01T11:55:46.026577",
  "updatedAt": "2025-02-01T11:55:46.026587"
}
```

#### 1. **Get All Scheduled Transfer**

**GET /api/transfer/schedules/{senderAccountId}**

**Response Body:**
```json
[
  {
    "transferId": "8b0f58ca-1f2e-4d57-acb5-cd413eb04348",
    "senderAccountId": "6238411689",
    "recipientAccountId": "1853614738",
    "transferAmount": 1,
    "transferDate": "2026-02-01T10:40:42.006102",
    "status": "PENDING",
    "createdAt": "2025-02-01T11:37:07.233789",
    "updatedAt": "2025-02-01T11:37:07.233807"
  },
  {
    "transferId": "bb9f4bc7-b975-42f4-b187-a03fbd7e770f",
    "senderAccountId": "6238411689",
    "recipientAccountId": "1853614738",
    "transferAmount": 1,
    "transferDate": "2026-02-01T10:40:42.006102",
    "status": "PENDING",
    "createdAt": "2025-02-01T11:37:07.026184",
    "updatedAt": "2025-02-01T11:37:07.026192"
  }
]

```
#### 1. **Cancel a Scheduled Transfer**

**GET /api/transfer/schedules/cancel/{transferId}**

**Response Body:**
```json
{
  "transferId": "8b0f58ca-1f2e-4d57-acb5-cd413eb04348",
  "senderAccountId": "6238411689",
  "recipientAccountId": "1853614738",
  "transferAmount": 1,
  "transferDate": "2026-02-01T10:40:42.006102",
  "status": "CANCELED",
  "createdAt": "2025-02-01T11:37:07.233789",
  "updatedAt": "2025-02-01T11:37:07.233807"
}
```

:scroll: **END** 