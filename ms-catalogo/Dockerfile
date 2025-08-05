# Multi-stage build para ms-catalogo
FROM openjdk:17-jdk-slim

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
ENV JAVA_OPTS="-Xmx512m -Xms256m" \
    SPRING_APPLICATION_NAME=SERVICIO-CATALOGO \
    EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka/ \
    SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/catalogo_db \
    SPRING_DATASOURCE_USERNAME=admin \
    SPRING_DATASOURCE_PASSWORD=admin12345 \
    SPRING_RABBITMQ_HOST=rabbitmq \
    SPRING_RABBITMQ_PORT=5672 \
    SPRING_RABBITMQ_USERNAME=admin \
    SPRING_RABBITMQ_PASSWORD=admin

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]