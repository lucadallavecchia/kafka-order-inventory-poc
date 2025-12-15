# Kafka Order & Inventory PoC

🚧 **Work in Progress** 🚧

## Overview
This project is a **proof of concept** to demonstrate an **event-driven architecture** using **Apache Kafka** and **Spring Boot**.

## Idea
- **Order Service**: accepts new orders via REST API and publishes order events
- **Inventory Service**: consumes order events and keeps the product inventory up to date
- **Apache Kafka**: handles the asynchronous communication between services

The goal of this PoC is to showcase **decoupled microservices**, **event-based communication**, and **Kafka fundamentals** in a simple and easy-to-run setup.
