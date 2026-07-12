# ================================
# 1. Use official JDK image
# ================================
FROM eclipse-temurin:21-jdk

# ================================
# 2. Set working directory
# ================================
WORKDIR /app

# ================================
# 3. Copy Maven build output (JAR)
# ================================
COPY target/marinelink-portal.jar app.jar

# ================================
# 4. Expose backend port
# ================================
EXPOSE 8080

# ================================
# 5. Run the Spring Boot app
# ================================
ENTRYPOINT ["java", "-jar", "app.jar"]
