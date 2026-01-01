# Stream-Seat: Microservices Migration to AWS Fargate

## Project Overview
**Stream-Seat** is a distributed microservices platform designed for real-time ticket booking. This project documents the transition from a localized development environment to a production-grade, serverless **AWS Cloud** infrastructure.

## Cloud Architecture (The "50%" Milestone)
The project has successfully reached the 50% deployment milestone, moving from core networking setup to the active orchestration of service discovery and persistent storage integration.

### Current Infrastructure Components:
* **Compute:** AWS Fargate (Serverless) utilizing the **AMD64** runtime environment.
* **Service Discovery:** Netflix Eureka Server facilitating internal service-to-service communication via Private VPC routing.
* **Database:** Amazon RDS (MySQL) deployed within private subnets for enhanced data security.
* **Registry:** Amazon ECR for multi-region container image management and distribution.
* **Networking:** Custom VPC with a multi-AZ, split-subnet architecture (Public/Private).



---

## Technical Challenges & Solutions

### 1. Cross-Platform Container Compatibility
* **Issue:** `CannotPullContainerError: image Manifest does not contain descriptor matching platform 'linux/amd64'`.
* **Cause:** Development on Apple Silicon (ARM64) created an architecture mismatch when deploying to standard AMD64 Fargate nodes.
* **Fix:** Implemented **Docker multi-platform builds** using the `--platform linux/amd64` flag. This ensured that images built on ARM64 hardware remained fully compatible with cloud-native x86_64 runtimes.

### 2. Private Database Orchestration
* **Issue:** `SQLGrammarException: Unknown database` and connection timeouts during service startup.
* **Cause:** Strict VPC isolation prevented external schema initialization, and the application required a pre-existing schema for Hibernate DDL execution.
* **Fix:** Implemented **Security Group Nesting** to allow traffic specifically from the Application Security Group (`app-sg`) to the Database Security Group (`rds-sg`). Leveraged Spring Boot’s **Externalized Configuration** to map the JDBC connection to the default `mysql` schema for initial VPC handshake validation.

### 3. Internal Service Discovery Handshake
* **Issue:** Microservices successfully reached a `RUNNING` status but failed to appear in the Eureka registry.
* **Cause:** Communication was blocked by default Security Group ingress rules and hindered by the dynamic nature of Fargate Private IPs.
* **Fix:** Established a **Self-Referencing Security Group rule** for port 8761 and updated environment variables to use current **VPC Private IPs**. This ensured all registry traffic stayed within the AWS internal network, eliminating data egress costs and minimizing latency.

---

## Deployment Status

| Service | Status | Port | Networking | Registry |
| :--- | :--- | :--- | :--- | :--- |
| **Discovery Server (Eureka)** | **RUNNING** | 8761 | Public Subnet | ECR (us-east-1) |
| **Booking Service** | **RUNNING** | 8080 | Private Subnet | ECR (us-east-1) |
| **Event Service** | **IMAGE READY** | 8082 | Private Subnet | ECR (us-east-1) |
| **API Gateway** | **IMAGE READY** | 8080 | Public Subnet | ECR (us-east-1) |
| **RDS MySQL** | **AVAILABLE** | 3306 | Private Subnet | AWS RDS |

---

## Key Skills Demonstrated
* **Advanced Containerization:** Expertise in managing multi-architecture Docker manifests and ECR lifecycle policies.
* **Infrastructure Troubleshooting:** Proficiency in analyzing CloudWatch logs and Spring Boot stack traces to resolve network-level and application-level failures.
* **Cloud Security:** Implementation of the **Principle of Least Privilege** through granular Security Group rules and private network isolation.
* **Externalized Configuration:** Utilizing Spring Boot profiles and environment variable injection to maintain environment parity across local and cloud deployments.
