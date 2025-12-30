# Car Management & Fuel Tracking System

Java 17 Spring Boot REST API with manual HttpServlet and CLI client.

## Prerequisites

- **Java 17 or higher**
- **Maven 3.6+** (optional - Maven Wrapper included in backend)

### Verify Installation

**macOS / Linux:**
```bash
java -version    # Should show Java 17+
mvn -version     # Optional - only if not using wrapper
```

**Windows (PowerShell or CMD):**
```cmd
java -version
mvn -version
```

## Quick Start

### Step 1: Start Backend Server

**macOS / Linux:**
```bash
cd backend
./mvnw spring-boot:run
```

**Windows (PowerShell or CMD):**
```cmd
cd backend
mvnw.cmd spring-boot:run
```

**Alternative (if Maven is installed globally):**
```bash
cd backend
mvn spring-boot:run
```

Server starts on `http://localhost:8080`

### Step 2: Test Backend

**macOS / Linux:**
```bash
curl http://localhost:8080/health
```

**Windows:**
```cmd
curl http://localhost:8080/health
```

**Expected output:**
```json
{
  "status": "UP",
  "message": "Service is healthy",
  "timestamp": "2025-12-30T15:30:00"
}
```

## API Endpoints

**Base URL:** `http://localhost:8080`

### 1. Health Check

**GET** `/health` - Check if server is running

**cURL (macOS/Linux):**
```bash
curl http://localhost:8080/health
```

**cURL (Windows):**
```cmd
curl http://localhost:8080/health
```

**Response:** `200 OK`
```json
{
  "status": "UP",
  "message": "Service is healthy",
  "timestamp": "2025-12-30T15:30:00"
}
```

### 2. Create Car

**POST** `/api/cars` - Create a new car

**Request Body:**
```json
{
  "brand": "Toyota",
  "model": "Corolla",
  "year": 2018
}
```

**CLI (macOS/Linux):**
```bash
cd cli
mvn exec:java -Dexec.args="create-car --brand Toyota --model Corolla --year 2018"
```

**CLI (Windows):**
```cmd
cd cli
mvn exec:java "-Dexec.args=create-car --brand Toyota --model Corolla --year 2018"
```

**cURL (macOS/Linux):**
```bash
curl -X POST http://localhost:8080/api/cars \
  -H "Content-Type: application/json" \
  -d '{"brand":"Toyota","model":"Corolla","year":2018}'
```

**cURL (Windows PowerShell):**
```powershell
curl.exe -X POST http://localhost:8080/api/cars `
  -H "Content-Type: application/json" `
  -d '{\"brand\":\"Toyota\",\"model\":\"Corolla\",\"year\":2018}'
```

**cURL (Windows CMD):**
```cmd
curl -X POST http://localhost:8080/api/cars -H "Content-Type: application/json" -d "{\"brand\":\"Toyota\",\"model\":\"Corolla\",\"year\":2018}"
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "brand": "Toyota",
  "model": "Corolla",
  "year": 2018
}
```

### 3. List All Cars

**GET** `/api/cars` - Get all cars

**CLI (macOS/Linux):**
```bash
# Note: CLI doesn't have a list command, use cURL
curl http://localhost:8080/api/cars
```

**cURL (macOS/Linux):**
```bash
curl http://localhost:8080/api/cars
```

**cURL (Windows):**
```cmd
curl http://localhost:8080/api/cars
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "brand": "Toyota",
    "model": "Corolla",
    "year": 2018
  }
]
```

### 4. Add Fuel Entry

**POST** `/api/cars/{id}/fuel` - Add fuel entry to a car

**Request Body:**
```json
{
  "liters": 40.5,
  "price": 1.50,
  "odometer": 45000
}
```

**CLI (macOS/Linux):**
```bash
cd cli
mvn exec:java -Dexec.args="add-fuel --carId 1 --liters 40 --price 1.50 --odometer 45000"
```

**CLI (Windows):**
```cmd
cd cli
mvn exec:java "-Dexec.args=add-fuel --carId 1 --liters 40 --price 1.50 --odometer 45000"
```

**cURL (macOS/Linux):**
```bash
curl -X POST http://localhost:8080/api/cars/1/fuel \
  -H "Content-Type: application/json" \
  -d '{"liters":40,"price":1.50,"odometer":45000}'
```

**cURL (Windows PowerShell):**
```powershell
curl.exe -X POST http://localhost:8080/api/cars/1/fuel `
  -H "Content-Type: application/json" `
  -d '{\"liters\":40,\"price\":1.50,\"odometer\":45000}'
```

**cURL (Windows CMD):**
```cmd
curl -X POST http://localhost:8080/api/cars/1/fuel -H "Content-Type: application/json" -d "{\"liters\":40,\"price\":1.50,\"odometer\":45000}"
```

**Response:** `201 Created` (no body)

### 5. Get Fuel Statistics (REST API)

**GET** `/api/cars/{id}/fuel/stats` - Get fuel statistics for a car

**CLI (macOS/Linux):**
```bash
cd cli
mvn exec:java -Dexec.args="fuel-stats --carId 1"
```

**CLI (Windows):**
```cmd
cd cli
mvn exec:java "-Dexec.args=fuel-stats --carId 1"
```

**cURL (macOS/Linux):**
```bash
curl http://localhost:8080/api/cars/1/fuel/stats
```

**cURL (Windows):**
```cmd
curl http://localhost:8080/api/cars/1/fuel/stats
```

**Response:** `200 OK`
```json
{
  "totalFuel": 40.0,
  "totalCost": 60.0,
  "avgConsumptionPer100km": 0.0
}
```

### 6. Get Fuel Statistics (Servlet)

**GET** `/servlet/fuel-stats?carId={id}` - Get fuel statistics via manual servlet

**cURL (macOS/Linux):**
```bash
curl "http://localhost:8080/servlet/fuel-stats?carId=1"
```

**cURL (Windows PowerShell):**
```powershell
curl.exe "http://localhost:8080/servlet/fuel-stats?carId=1"
```

**cURL (Windows CMD):**
```cmd
curl "http://localhost:8080/servlet/fuel-stats?carId=1"
```

**Response:** `200 OK` (same JSON as endpoint #5)

## Complete CLI Commands Reference

**macOS / Linux:**
```bash
cd cli

# Create car
mvn exec:java -Dexec.args="create-car --brand Toyota --model Corolla --year 2018"

# Add fuel entry
mvn exec:java -Dexec.args="add-fuel --carId 1 --liters 40 --price 1.50 --odometer 45000"

# Get fuel statistics
mvn exec:java -Dexec.args="fuel-stats --carId 1"
```

**Windows (PowerShell or CMD):**
```cmd
cd cli

# Create car
mvn exec:java "-Dexec.args=create-car --brand Toyota --model Corolla --year 2018"

# Add fuel entry
mvn exec:java "-Dexec.args=add-fuel --carId 1 --liters 40 --price 1.50 --odometer 45000"

# Get fuel statistics
mvn exec:java "-Dexec.args=fuel-stats --carId 1"
```

**Note:** If Maven is not installed, use the backend's wrapper:
```bash
# macOS/Linux
../backend/mvnw exec:java -Dexec.args="create-car --brand Toyota --model Corolla --year 2018"

# Windows
..\backend\mvnw.cmd exec:java "-Dexec.args=create-car --brand Toyota --model Corolla --year 2018"
```

## Complete cURL Commands Reference

**macOS / Linux:**
```bash
# Health check
curl http://localhost:8080/health

# Create car
curl -X POST http://localhost:8080/api/cars \
  -H "Content-Type: application/json" \
  -d '{"brand":"Toyota","model":"Corolla","year":2018}'

# List all cars
curl http://localhost:8080/api/cars

# Add fuel entry
curl -X POST http://localhost:8080/api/cars/1/fuel \
  -H "Content-Type: application/json" \
  -d '{"liters":40,"price":1.50,"odometer":45000}'

# Get fuel stats (REST API)
curl http://localhost:8080/api/cars/1/fuel/stats

# Get fuel stats (Servlet)
curl "http://localhost:8080/servlet/fuel-stats?carId=1"
```

**Windows (PowerShell):**
```powershell
# Health check
curl.exe http://localhost:8080/health

# Create car
curl.exe -X POST http://localhost:8080/api/cars `
  -H "Content-Type: application/json" `
  -d '{\"brand\":\"Toyota\",\"model\":\"Corolla\",\"year\":2018}'

# List all cars
curl.exe http://localhost:8080/api/cars

# Add fuel entry
curl.exe -X POST http://localhost:8080/api/cars/1/fuel `
  -H "Content-Type: application/json" `
  -d '{\"liters\":40,\"price\":1.50,\"odometer\":45000}'

# Get fuel stats (REST API)
curl.exe http://localhost:8080/api/cars/1/fuel/stats

# Get fuel stats (Servlet)
curl.exe "http://localhost:8080/servlet/fuel-stats?carId=1"
```

**Windows (CMD):**
```cmd
curl http://localhost:8080/health

curl -X POST http://localhost:8080/api/cars -H "Content-Type: application/json" -d "{\"brand\":\"Toyota\",\"model\":\"Corolla\",\"year\":2018}"

curl http://localhost:8080/api/cars

curl -X POST http://localhost:8080/api/cars/1/fuel -H "Content-Type: application/json" -d "{\"liters\":40,\"price\":1.50,\"odometer\":45000}"

curl http://localhost:8080/api/cars/1/fuel/stats

curl "http://localhost:8080/servlet/fuel-stats?carId=1"
```

## Error Handling

All errors return consistent JSON format:

```json
{
  "status": 400,
  "message": "Error message",
  "timestamp": "2024-12-30T15:30:00",
  "path": "/api/cars"
}
```

**Status Codes:**
- `200` - Success
- `201` - Created
- `400` - Bad Request (invalid input, malformed JSON)
- `404` - Not Found (car doesn't exist, wrong endpoint)
- `500` - Internal Server Error

## Troubleshooting

### Backend won't start
- **Port 8080 in use:** Change port in `backend/src/main/resources/application.properties`
- **Java version:** Verify with `java -version` (must be 17+)
- **Maven wrapper permissions (macOS/Linux):** Run `chmod +x backend/mvnw`

### CLI can't connect
- **Backend not running:** Ensure backend is running on `http://localhost:8080`
- **Maven not found:** Use Maven wrapper from backend directory or install Maven globally

### Build issues
- **Stale compiled classes:** Run `mvn clean` before building
- **Unexpected behavior after code changes:** Clean and rebuild with `mvn clean compile` or `mvn clean spring-boot:run`

### When to use `mvn clean`
`mvn clean` removes the `target/` directory (compiled classes). Use it when:
- **First time setup:** Ensures fresh build
- **Strange compilation errors:** Clears cached build artifacts
- **Before packaging:** Ensures clean JAR/WAR creation
- **After pulling changes:** Ensures you're using latest code

**Normal development:** Not required - Maven automatically recompiles changed files.

**Examples:**
```bash
# Clean and compile
mvn clean compile

# Clean and run backend
mvn clean spring-boot:run

# Clean and package
mvn clean package
```

### Windows-specific issues
- **PowerShell execution policy:** Run `Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser` if wrapper fails
- **Line continuation:** Use backtick `` ` `` in PowerShell, `^` in CMD

## Technology Stack

- Java 17
- Spring Boot 3.5.9
- Maven
- In-memory storage (ConcurrentHashMap)
- java.net.http.HttpClient (CLI)
