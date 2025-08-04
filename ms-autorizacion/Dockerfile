# Multi-stage build para ms-autorizacion
FROM openjdk:17-jdk-slim

# Instalar curl para health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Crear usuario no-root para seguridad
RUN addgroup --system spring && adduser --system spring --ingroup spring

WORKDIR /app

# Copiar el JAR compilado
COPY target/*.jar app.jar

# Cambiar propietario del archivo
RUN chown spring:spring app.jar

# Cambiar a usuario no-root
USER spring:spring

# Exponer puerto dinámico (será asignado por Kubernetes)
EXPOSE 8080

# Variables de entorno para Kubernetes
ENV JAVA_OPTS="-Xmx512m -Xms256m" \
    SPRING_APPLICATION_NAME=SERVICIO-AUTORIZACION \
    EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka/ \
    SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/autorizacion_db \
    SPRING_DATASOURCE_USERNAME=admin \
    SPRING_DATASOURCE_PASSWORD=admin12345 \
    JWT_SECRET=a-string-secret-at-least-256-bits-long \
    JWT_EXPIRATION_MS=3600000

# Health check para Kubernetes
HEALTHCHECK --interval=30s --timeout=10s --start-period=90s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]