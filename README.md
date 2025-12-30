# Stream-Seat: Microservices Migration to AWS Fargate

[![AWS](https://img.shields.io/badge/AWS-Fargate-orange?logo=amazon-aws)](https://aws.amazon.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?logo=springboot)](https://spring.io/projects/spring-boot)
[![Eureka](https://img.shields.io/badge/Service%20Discovery-Eureka-blue)](https://github.com/Netflix/eureka)

## Project Overview
**Stream-Seat** is a distributed microservices platform for real-time ticket booking. This repository documents the transition from a local development environment to a production-grade **AWS Cloud** infrastructure.

## Cloud Infrastructure (The "25%" Milestone)
I have successfully established the networking backbone and service discovery layer.

### **Current Infrastructure Components:**
* **Custom VPC:** `microservices-vpc` with a 10.0.0.0/16 CIDR block.
* **Network Partitioning:** 2 Public Subnets (for Discovery/Gateway) and 2 Private Subnets (for Microservices/Database) across `us-east-1a` and `us-east-1b`.
* **Service Discovery:** Netflix Eureka deployed as a serverless container on **AWS Fargate**.
* **Registry:** Multi-region image management via **Amazon ECR**.



---

## Technical Challenges & Solutions

### 1. Solving the `ResourceInitializationError`
* **Issue:** Fargate tasks failed to pull images from ECR due to an `i/o timeout`.
* **Cause:** Tasks in the custom VPC lacked a public IP and a route to the Internet Gateway.
* **Fix:** Enabled **Auto-assign Public IPv4** on subnets and updated the **Route Table** to point `0.0.0.0/0` to the Internet Gateway.

### 2. Registry Auth & URI Configuration
* **Issue:** `CannotPullContainerError` (Not Found).
* **Fix:** Corrected the Image URI in the **ECS Task Definition** to point to the `us-east-1` regional registry instead of the local build path.

---

## Deployment Status
| Service | Status | Port | Networking |
| :--- | :--- | :--- | :--- |
| **Discovery Server (Eureka)** | RUNNING | 8761 | Public Subnet |
| **Booking Service** | IMAGE READY | 8081 | Pending RDS Link |
| **Event Service** | IMAGE READY | 8082 | Pending RDS Link |
| **API Gateway** | IMAGE READY | 8080 | Pending RDS Link |
| **RDS MySQL** | CONFIGURING | 3306 | Private Subnet |

---

## Key Skills Demonstrated
- **Cloud Networking:** VPC design, Subnetting, Route Tables, and Internet Gateways.
- **Containerization:** Dockerizing Spring Boot apps and managing ECR repositories.
- **Troubleshooting:** Deep-diving into CloudWatch logs to resolve deployment "Circuit Breaker" triggers.

