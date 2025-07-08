# ⛅️ Weather Info API

This is a Spring Boot REST API that provides **weather data** (temperature, humidity, description, wind speed) based on a **given Indian PIN code and date**, by integrating with the **OpenWeatherMap API**. It caches weather data for future requests.

---

## ✨ Features

* ✅ Get real-time weather by PIN code and date
* ✅ Uses OpenWeatherMap API (Geo & Weather)
* ✅ Automatic caching in DB (no repeat calls)
* ✅ JPA entities for `Pincode` and `Weather`
* ✅ Global exception handling
* ✅ Logs major operations (API calls, DB saves)
* ✅ RESTful structure with DTOs
* ✅ Clean, modular design

---

## ⚙️ Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* H2 Database (can be changed to MySQL/Postgres)
* OpenWeatherMap API
* RestTemplate
* SLF4J (Logging)

---

## 📂 Project Structure

```
com.weatherapp
├── controller
├── config
├── dtos
├── entity
├── repository
├── service
│   └── impl
└── exception
```

---

## 🔐 Prerequisites

* Java 21
* Maven
* OpenWeatherMap API Key ([Sign up here](https://openweathermap.org/api))

---

## 🛠️ Setup & Run

1. Clone the project:

```bash
git clone https://github.com/sandeepswain12/weather-api
```

2. In `application.properties`, add:

```properties
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
openweather.api.key=YOUR_API_KEY
```

3. Run the app:

```bash
./mvnw spring-boot:run
```

Runs by default on: `http://localhost:8080`

---

## 📬 API Endpoint

### `POST /api/weather`

#### 🔹 Request Body:

```json
{
  "pincode": "110001",
  "forDate": "2025-07-08"
}
```

#### 🔹 Response:

```json
{
  "pincode": "110001",
  "date": "2025-07-08",
  "temperature": 32.5,
  "description": "clear sky",
  "humidity": 65,
  "windSpeed": 2.5
}
```

#### 🔺 Error (Invalid Pincode or API issue):

```json
{
  "message": "Google API call failed: No route found",
  "timestamp": "2025-07-08T14:26:28.6798003",
  "status": 400
}
```

---

## 📅 Caching

* If a weather record for the pincode+date already exists, it is returned directly from the DB.
* Only missing combinations call OpenWeatherMap and are then saved to DB.

---

## 👨‍💼 Author

**Sandeep Swain**
*Java Backend Developer | Spring Boot Enthusiast*

---

