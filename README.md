# Secure CI Pipeline — MIM736 Assignment 1

**Student:** Vincent Marufu (R259980C)  
**Group Members:** Buhlebenkosi Masuku (R2512144V), Noliet Gorejena (R2512607G), Tafadzwa Jacob Dundu (R227384Y), Rosemary Jindu (R2512406A)  
**Course:** MIM736 — MSc Information Systems  
**Repository:** https://github.com/vincentsimonmarufu/secure-ci-app

---

## Overview

A secure, automated Continuous Integration pipeline for a Java-based e-commerce Order Price Calculator application. The pipeline enforces code quality, security scanning, and container best practices at every stage.

---

## Application

The application is an **Order Price Calculator** that:
- Calculates order subtotals based on quantity and unit price
- Applies a 10% bulk discount for orders of 10 or more items
- Adds 15% tax to the discounted amount
- Generates a formatted order summary

---

## Project Structure
```
secure-ci-app/
├── .github/
│   └── workflows/
│       └── ci.yml              ← GitHub Actions pipeline
├── src/
│   ├── main/java/com/myapp/
│   │   ├── App.java            ← Application entry point
│   │   ├── Order.java          ← Order data model
│   │   └── OrderService.java   ← Business logic
│   └── test/java/com/myapp/
│       └── AppTest.java        ← 8 JUnit 5 unit tests
├── checkstyle.xml              ← Checkstyle rules
├── Dockerfile                  ← Multi-stage Docker build
└── pom.xml                     ← Maven configuration
```

---

## Task 1 — Repository Setup and Secure SCM

- **Protected branch:** `main` branch is protected via GitHub Ruleset
- **Direct pushes blocked:** All changes must go through a Pull Request
- **Required reviewers:** At least 1 approving review required before merge
- **Pre-commit hook:** Checkstyle runs locally before every commit via `.git/hooks/pre-commit`

### Running Checkstyle manually
```bash
mvn checkstyle:check
```

---

## Task 2 — CI Pipeline (GitHub Actions)

The pipeline is defined in `.github/workflows/ci.yml` and triggers on every push and pull request.

### Pipeline Stages

| Stage | Command | Purpose |
|-------|---------|---------|
| Build | `mvn compile` | Compiles Java source code |
| Test | `mvn test` | Runs 8 JUnit 5 unit tests |
| SAST | `mvn checkstyle:check` | Static analysis and style enforcement |
| Cache | `actions/cache@v4` | Caches Maven dependencies via pom.xml hash |

### Running the pipeline locally
```bash
mvn test
```

---

## Task 3 — Containerisation and Security Hardening

### Build the Docker image
```bash
docker build -t secure-ci-app .
```

### Run the container
```bash
docker run --name secure-ci-container secure-ci-app
```

### View logs
```bash
docker logs secure-ci-container
```

### Verify non-root user
```bash
docker run --rm --entrypoint whoami secure-ci-app
# Expected output: appuser
```

### Security hardening applied
- Multi-stage build — only compiled JAR in final image
- Minimal base image — `eclipse-temurin:21-jre-alpine`
- Non-root user — application runs as `appuser`
- No hardcoded secrets — no API keys or passwords in image

---

## Access

This repository is public and accessible at:  
 https://github.com/vincentsimonmarufu/secure-ci-app