# Multi-stage build para optimizar tamaño
FROM openjdk:17-jdk-slim as builder

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

# Exponer puerto
EXPOSE 8761

# Variables de entorno para Kubernetes
ENV JAVA_OPTS="-Xmx512m -Xms256m" \
    EUREKA_INSTANCE_HOSTNAME=eureka-server \
    EUREKA_CLIENT_REGISTER_WITH_EUREKA=false \
    EUREKA_CLIENT_FETCH_REGISTRY=false \
    SERVER_PORT=8761

# Health check para Kubernetes readiness/liveness probes
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8761/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]