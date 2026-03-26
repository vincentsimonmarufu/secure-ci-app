# ================================
# Stage 1 — Build
# ================================
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set working directory inside the container
WORKDIR /app

# Copy Maven config first (allows dependency caching)
COPY pom.xml .
COPY checkstyle.xml .

# Download dependencies separately (cached if pom.xml unchanged)
RUN apt-get install -y maven 2>/dev/null || \
    wget -q https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz && \
    tar -xf apache-maven-3.9.6-bin.tar.gz && \
    mv apache-maven-3.9.6 /opt/maven

ENV PATH="/opt/maven/bin:${PATH}"

# Copy source code
COPY src ./src

# Build the JAR file, skip tests (tests run in CI pipeline)
RUN mvn package -DskipTests -Dcheckstyle.skip=true

# ================================
# Stage 2 — Runtime
# ================================
FROM eclipse-temurin:21-jre-alpine

# Create a non-root user (security hardening)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Set working directory
WORKDIR /app

# Copy only the JAR from the build stage
COPY --from=builder /app/target/secure-ci-app-1.0-SNAPSHOT.jar app.jar

# Switch to non-root user
USER appuser

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]