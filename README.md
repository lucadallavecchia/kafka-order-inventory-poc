# Kafka Order & Inventory PoC

This project is a **Proof of Concept (PoC)** designed to demonstrate an **event-driven architecture** using **Apache Kafka** and **Spring Boot**. The goal is to manage eventual consistency between microservices in an asynchronous way.



## 🚀 Architecture & Event Flow

The system consists of two main microservices that communicate via Kafka:

1.  **Order Service**:
    * Receives an order via a REST API.
    * Persists the order in its database (PostgreSQL) with a `PENDING` status.
    * Publishes an event to the `order-placed-topic`.
    * Listens to the `inventory-response-topic` to update the final status to `COMPLETED` or `FAILED`.
2.  **Inventory Service**:
    * Consumes events from the `order-placed-topic`.
    * Checks product availability in its own database (PostgreSQL).
    * Decrements stock and sends a success response if available; otherwise, sends a failure message to the `inventory-response-topic`.



## 🛠 Tech Stack
* **Java 21** & **Spring Boot 3**
* **Apache Kafka** (Asynchronous messaging)
* **PostgreSQL** (Separate databases for each service to ensure decoupling)
* **Docker & Docker Compose** (Containerization)
* **MapStruct** (DTO Mapping)
* **Lombok** (Boilerplate reduction)

## 📁 Project Structure
```text
kafka-order-inventory-poc/
├── inventory-service/      # Inventory and stock management
├── order-service/          # Order management and status tracking
├── docker-compose.yaml     # Infrastructure setup (Kafka, Zookeeper, DBs)
└── Kafka Order Inventory.postman_collection.json # API Testing kit
```

## 🚦 Getting Started

### Prerequisites
* **Docker** and **Docker Compose** installed on your machine.
* **Java 21** (if you wish to run services outside of Docker).

### Run the Project
From the project root directory, execute the following commands:

```bash
# Build and start all containers (use this if you modified code or properties)
docker-compose up --build

# Start containers without rebuilding
docker-compose up -d
```

## 🧪 Testing with Postman

A Postman collection is included in the root directory: `Kafka Order Inventory.postman_collection.json`. Import it into Postman to test the full event-driven flow.

### 1. Inventory Setup
* **Update Stock (POST)**: Initialize or update product quantities in the Inventory Service.
    * `URL: http://localhost:8081/api/inventory/stock-update`
* **Get Stock (GET)**: Verify current stock levels for all products.
    * `URL: http://localhost:8081/api/inventory`

### 2. Order Workflow
* **Create Order (POST)**: Place a new order. The Order Service saves it as `PENDING` and publishes a Kafka event.
    * `URL: http://localhost:8080/api/orders`
    * **Example Body**:
      ```json
      {
          "customerId": "CUST-LUCAA",
          "items": [
            {
              "productId": "350e8400-e29b-41d4-a716-446655440000",
              "quantity": 150,
              "price": 1200.50
            }
          ]
      }
      ```
* **Get Orders (GET)**: Retrieve the list of all orders to verify the asynchronous status update.
    * `URL: http://localhost:8080/api/orders`
    * **Note**: Observe the `status` field change from `PENDING` to `COMPLETED` (if stock is sufficient) or `FAILED` (if stock is insufficient) after the Kafka round-trip.


## Kafka UI
A web-based **Kafka UI** is included in the Docker Compose setup and is available at:

👉 **http://localhost:8090/**

### What is Kafka UI?
Kafka UI is a web interface that allows you to **inspect and manage Kafka resources** in real time without using command-line tools.

In this PoC, it is mainly used for **debugging and learning purposes**.

### What you can do with Kafka UI:
- View existing **Kafka topics**
- Inspect **messages** published by the Order Service
- Monitor **consumer groups** and their offsets
- Verify that the Inventory Service is correctly consuming events
- Better understand how events flow between services

Kafka UI makes it easy to visualize the event-driven communication and is especially useful when exploring Kafka concepts for the first time.
