# Pub-Sub Spring Boot Messaging System

A lightweight, Kafka-inspired Publish-Subscribe system built using Java 11, Spring Boot, WebFlux, and H2/MySQL support.  
The project mimics real-world Kafka producer/consumer behavior without relying on Kafka dependencies.

---

## 🔧 Tech Stack

- Java 11
- Spring Boot (2.7.x)
- Spring Web / WebFlux (SSE support)
- H2 (in-memory) / MySQL (persistent)
- JPA + Hibernate
- Lombok
- Maven

---

## 📦 Features

- ✅ Publisher-Topic-Subscriber model
- ✅ In-memory and MySQL support
- ✅ JSON string storage for flexible payloads
- ✅ UUID-based primary keys
- ✅ API-based event push (like Kafka producer)
- ✅ CLI-based persistent subscriber (like Kafka consumer)
- ✅ Global logging interceptor for all HTTP requests/responses
- ✅ SSE (Server-Sent Events) for real-time message delivery to subscribers

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

## 🚀 How to Run

### 1. Clone & Build

```bash
git clone git@github.com:Pranav-Thakur/pub-sub-springboot.git
cd pub-sub-springboot
./mvnw clean install
./mvnw spring-boot:run
```
```
🌐 API Endpoints
Method	    Endpoint	                               Description
POST	/api/v1/publisher/register	               Create new publisher
POST	/api/v1/publisher/{id}/create-topic	       Topic created by publisher by Id
POST	/api/v1/publisher/{id}/publish	           Send a message to topic by publisher Id
POST	/api/v1/user/register	                   User is created for the subscriber system, may not be needed
POST    /api/v1/user/{id}/subscribe                the consumer for the topic has to first subscribe
POST    /api/v1/user/consume?subscriberId=UUID     the actual consumer who gets the message
```
---
## 📬 Postman Collection

You can test all APIs using the provided Postman collection:

🔗 [Download Pub-Sub API Collection](postman/pub-sub-api.postman_collection.json)

> Import the JSON into Postman → Collections → Import

---

## 🧪 Testing with CMD Consumers
- Use Java CLI or curl with SSE-compatible headers to simulate long-lived consumers.
- curl http://localhost:8080/api/v1/user/consume?subscriberId=3d711ecb-48d0-4771-a368-47f9f0343df5

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