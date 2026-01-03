# Stream-Seat: Cloud-Native Microservices Migration

##  Project Overview
**Stream-Seat** is a distributed microservices platform designed for real-time ticket booking. This project documents the transition from a localized development environment to a production-grade, serverless **AWS Cloud** infrastructure, focusing on high availability and secure service orchestration.

---

##  Cloud Architecture (The "100% Infrastructure Milestone")
The project has successfully reached full infrastructure deployment, transitioning from manual task management to a fully routed, discovery-aware microservices ecosystem.

### Current Infrastructure Components:
* **Compute:** AWS ECS Fargate (Serverless) utilizing the **AMD64** runtime environment.
* **Service Discovery:** **AWS Cloud Map** with Private DNS Namespaces (`stream-seat.local`) for stable, hostname-based internal routing.
* **API Gateway:** **Spring Cloud Gateway** deployed in public subnets as the unified entry point for all external traffic.
* **Database:** Amazon RDS (MySQL 8.0) isolated in private subnets with administrative access via **EC2 Bastion Hosts**.
* **Networking:** Custom VPC with multi-AZ redundancy and **Security Group Nesting** for zero-trust inter-service communication.



---

## 🛠️ Technical Challenges & Solutions

### 1. Cross-Platform Container Compatibility
* **Issue:** `CannotPullContainerError` due to ARM64 (Apple Silicon) vs. AMD64 (AWS Fargate) architecture mismatch.
* **Fix:** Implemented **Docker multi-platform builds** using the `--platform linux/amd64` flag to ensure binary compatibility across development and cloud environments.

### 2. Private Database Orchestration & Schema Initialization
* **Issue:** Security isolation prevented RDS schema creation, causing `SQLGrammarException` during service startup.
* **Fix:** Utilized a **Bastion Host (EC2)** to bridge into the private subnet. Manually initialized the `stream_seat_db` schema to support Hibernate's DDL execution while maintaining a restricted database attack surface.

### 3. Non-Routable Link-Local Address Conflict
* **Issue:** Fargate tasks registered with Eureka using `169.254.x.x` addresses, which are non-routable between different tasks, causing "Connection Refused" errors at the Gateway.
* **Fix:** Configured **Spring Cloud InetUtils** via environment variables (`SPRING_CLOUD_INETUTILS_PREFERRED_NETWORKS=10.0`) to force services to register using their routable VPC Private IPs.

### 4. Ephemeral IP Management
* **Issue:** Dynamic Fargate IPs broke hardcoded service links on every task restart, leading to frequent manual configuration updates.
* **Fix:** Integrated **AWS Cloud Map** to provide a persistent Private DNS record (`eureka.stream-seat.local`), decoupling the registry location from its ephemeral IP address.

---

## 🚦 Deployment Status

| Service | Status | Port | Networking | Registry |
| :--- | :--- | :--- | :--- | :--- |
| **Discovery Server (Eureka)** | **RUNNING** | 8761 | Public Subnet | ECR (us-east-1) |
| **API Gateway** | **RUNNING** | 8082 | Public Subnet | ECR (us-east-1) |
| **Booking Service** | **RUNNING** | 8080 | Private Subnet | ECR (us-east-1) |
| **Event Service** | **RUNNING** | 8081 | Private Subnet | ECR (us-east-1) |
| **RDS MySQL** | **AVAILABLE** | 3306 | Private Subnet | AWS RDS |

---

## 📈 Future Roadmap (CI/CD)
* [ ] **GitHub Actions Integration:** Automate the build-test-push cycle for all microservices.
* [ ] **Automated Deployment:** Configure ECS task definitions to update automatically upon new image pushes.
* [ ] **Secret Management:** Move AWS credentials and database passwords to **GitHub Secrets** and **AWS Secrets Manager**.

---

## 🛠️ Key Skills Demonstrated
* **Cloud Orchestration:** Managing complex service lifecycles via AWS ECS and Cloud Map.
* **Network Security:** Designing multi-tier VPCs and implementing **Bastion Host** architectures.
* **Infrastructure Troubleshooting:** Debugging Link-Local networking and cross-architecture container issues.
* **Externalized Configuration:** Utilizing Spring Boot environment variables to override production settings dynamically without image rebuilds.

---
