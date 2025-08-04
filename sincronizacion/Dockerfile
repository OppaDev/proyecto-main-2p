# Multi-stage build para sincronizacion
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

# Exponer puerto dinámico
EXPOSE 8080

# Variables de entorno para Kubernetes
ENV JAVA_OPTS="-Xmx256m -Xms128m" \
    SPRING_APPLICATION_NAME=SERVICIO-SINCRONIZACION \
    EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka/ \
    SPRING_RABBITMQ_HOST=rabbitmq \
    SPRING_RABBITMQ_PORT=5672 \
    SPRING_RABBITMQ_USERNAME=admin \
    SPRING_RABBITMQ_PASSWORD=admin \
    RELOJ_INTERVALO=10

# Health check para Kubernetes
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]