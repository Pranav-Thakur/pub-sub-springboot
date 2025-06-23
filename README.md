# 📦 Spring Boot Pub-Sub Messaging System

A lightweight, message-driven backend service built with Spring Boot using Java 11, WebFlux, and H2/MySQL support, designed to deliver messages to subscribers using an offset-based pub-sub mechanism (like Kafka, but simpler and lighter).

---
## 🛠 Tech Stack

| Layer        | Tech                  |
|--------------|-----------------------|
| Backend      | Spring Boot (Java 11) |
| Build Tool   | Maven                 |
| Library      | JPA,Hibernate,Lombok  |
| Database     | MySQL (via Railway)   |
| Deployment   | Docker + Render       |
| CI/CD        | GitHub Actions        |
| Secrets      | Render Secret Files   |
| Testing Tool | Postman               |

---

## 🚀 Features

- 📬 Publish-Subscribe message system using REST APIs, JSON flexible payloads
- 🧭 Offset-based message retrieval for each subscriber
- ✅ CLI-based persistent subscriber SSE (Server-Sent Events), (like Kafka consumer)
- 🔐 Environment-specific config with `application-prod.yml`
- 🐳 Dockerized and deployed on Render (Singapore region)
- 🛢️ MySQL integration via Railway (free-tier)
- 🔄 CI/CD via GitHub Actions + Render deploy hook

---

## 📁 Project Structure
```
src/
├── main/
│ ├── java/com/example/pubsub/
│ │ ├── controller/
│ │ ├── entity/
│ │ ├── repository/
│ │ ├── service/
│ │ └── payload/request & response/
│ ├── resources/
   ├── application.yml
   └── schema.sql (for MySQL)
│── postman/collection-file.json
│── Dockerfile, GitAction ci/cd

```

---

## 🗃️ Database Tables

| Table       | Columns                                                                 |
|-------------|-------------------------------------------------------------------------|
| publisher   | id, company_name, info (JSON), status, created_date, updated_date       |
| user        | id, name, info (JSON), status, created_date, updated_date               |
| topic       | id, publisher_id (FK), topic_name, ...                                  |
| subscriber  | id, user_id (FK), topic_id (FK), last_message_delivered_date            |
| message     | id, topic_id (FK), data (JSON), created_date                             |
| transaction | id, message_id (FK), subscriber_id (FK)                                 |

> All tables include: `UUID id`, `status`, `created_date`, `updated_date`, and `version`

---

## ⚙️ Build & Run Locally

### 🧪 Prerequisites

- Java 11
- Maven
- MySQL (local or Railway)

```bash
git clone git@github.com:Pranav-Thakur/pub-sub-springboot.git
cd pub-sub-springboot

./mvnw clean install
./mvnw spring-boot:run

mvn clean package -DskipTests
java -jar target/app.jar --spring.profiles.active=prod

```
---

## 🐳 Docker Setup
### 🔨 Build Docker Image
- docker build -t pubsub-app .
### 🚀 Run Container
- docker run -p 8080:8080 pubsub-app

---

## ☁️ Cloud Deployment (Render)
- Connect GitHub repo to Render
- Use Docker deployment option
- Add these secret files (in Render dashboard):

### secret.properties
- db.username=YOUR_USERNAME
- db.password=YOUR_PASSWORD
- db.url=<jdbc:mysql-url>
### In application-prod, reference:
- spring.config.import: optional:file:/etc/secrets/secret.properties

---

## 🛢️ MySQL on Railway
- Railway auto-sleeps when idle
- Grab DB connection info from Railway dashboard
- Schema must be manually initialized OR use:

---

## 🔁 CI/CD via GitHub Actions
- Location: .github/workflows/render-ci.yml

### What It Does:
- On push to main:
- Builds the app
- Uploads the artifact
- Triggers Render deployment via webhook

### 🔐 Webhook is stored in GitHub Secrets as RENDER_DEPLOY_HOOK

---

```
🌐 API Endpoints
Method	    Endpoint	                               Description
POST	/api/v1/publisher/register	               Create new publisher
POST	/api/v1/publisher/{id}/create-topic	       Topic created by publisher by Id
POST	/api/v1/publisher/{id}/publish	           Send a message to topic by publisher Id
POST	/api/v1/user/register	                   User is created for the subscriber system, may not be needed
POST    /api/v1/user/{id}/subscribe                the consumer for the topic has to first subscribe
POST    /api/v1/user/consume?subscriberId=UUID&offsetTime=time     the actual consumer who gets the message, offset not required, if present => time reference
```
---
## 📬 Postman Collection for 🔍 API Testing

Use the Postman collection included in the repo (postman_collection.json), Import into Postman to Test endpoints like:
- POST /publish
- POST /subscribe
- GET /messages/{subscriberId}

### You can test all APIs using the provided Postman collection:

🔗 [Download Pub-Sub API Collection](postman/pub-sub-api.postman_collection.json)

> Import the JSON into Postman → Collections → Import

---

## 🧪 Testing with CMD Consumers
- Use Java CLI or curl with SSE-compatible headers to simulate long-lived consumers.
- Local: curl http://localhost:8080/api/v1/user/consume?subscriberId=3d711ecb-48d0-4771-a368-47f9f0343df5
- Prod : curl --location --request GET 'https://pub-sub-app.onrender.com/api/v1/user/consume?subscriberId=c62a6505-331c-4ea2-963e-540263bcd968'

---

## 🛠 Configuration Notes
- For H2 in-memory: Set in application.yml
- For MySQL: Update application.yml with DB credentials
- Use column type BINARY(16) for UUIDs
- Ensure mysql-connector-java is added in pom.xml

---

## 🙏 Acknowledgements
- Inspired by HIT: The First Case and Kafka's event-driven architecture.
- Built for learning and exploring full-stack system design with Spring Boot.

## 📌 Author
- Pranav Thakur | Software Engineer | Systems Thinker
---