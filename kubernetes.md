# Despliegue de Microservicios en Kubernetes

Este documento contiene las instrucciones para desplegar y usar la arquitectura de microservicios en Kubernetes usando Minikube.

## Prerrequisitos

- Docker Desktop instalado y funcionando
- Minikube instalado
- kubectl instalado
- Java 17 JDK instalado

## Arquitectura Desplegada

### Infraestructura
- **PostgreSQL**: Base de datos principal
- **RabbitMQ**: Message broker para comunicación asíncrona
- **Eureka Server**: Service discovery

### Microservicios
- **ms-autorizacion** (2 replicas): Gestión de usuarios, roles y JWT
- **ms-publicaciones** (2 replicas): Gestión de autores, libros y artículos
- **ms-catalogo** (2 replicas): Catálogo de productos
- **ms-notificaciones** (2 replicas): Sistema de notificaciones
- **sincronizacion** (1 replica): Servicio de sincronización
- **api-gateway** (2 replicas): Gateway principal con enrutamiento

## Instrucciones de Despliegue

### 1. Iniciar Minikube

```bash
minikube start
```

### 2. Habilitar Ingress Controller

```bash
minikube addons enable ingress
```

### 3. Crear Namespace

```bash
kubectl create namespace aplicacion-distribuida
```

### 4. Desplegar Infraestructura y Microservicios

Navegar al directorio de infraestructura:
```bash
cd "kubernetes/infrastructura"
```

Desplegar en orden:
```bash
# Infraestructura
kubectl apply -f 1postgres.yml
kubectl apply -f 2rabbitmq.yml
kubectl apply -f 3eureka-server.yml

# Microservicios
kubectl apply -f 4ms-autorizacion.yml
kubectl apply -f 5ms-publicaciones.yml
kubectl apply -f 6ms-catalogo.yml
kubectl apply -f 7ms-notificaciones.yml
kubectl apply -f 8sincronizacion.yml
kubectl apply -f 9api-gateway.yml
```

### 5. Configurar Acceso Externo

#### Configurar Hosts
Agregar al archivo `C:\Windows\System32\drivers\etc\hosts`:
```
127.0.0.1    oppadev.io
127.0.0.1    www.oppadev.io
```

#### Iniciar Túnel de Minikube
```bash
minikube tunnel
```
> **Nota**: Mantener este comando ejecutándose en una terminal separada.

### 6. Verificar Despliegue

```bash
# Verificar todos los pods
kubectl get pods -n aplicacion-distribuida

# Verificar servicios
kubectl get svc -n aplicacion-distribuida

# Verificar ingress
kubectl get ingress -n aplicacion-distribuida
```

## Acceso a la Aplicación

### URL Principal
- **Dominio**: `http://oppadev.io` o `http://www.oppadev.io`
- **API Gateway Health**: `http://oppadev.io/actuator/health`

### Endpoints de Microservicios

#### Autorización y Roles
- **Login**: `POST http://oppadev.io/api/v1/auth/login`
- **Register**: `POST http://oppadev.io/api/v1/auth/register`
- **Roles**: `GET http://oppadev.io/api/v1/roles`

#### Publicaciones
- **Autores**: `GET/POST http://oppadev.io/api/v1/autor`
- **Libros**: `GET/POST http://oppadev.io/api/v1/libro`
- **Artículos**: `GET/POST http://oppadev.io/api/v1/articulo`

#### Catálogo
- **Productos**: `GET/POST http://oppadev.io/api/v1/catalogo`

#### Notificaciones
- **Notificaciones**: `GET/POST http://oppadev.io/api/v1/notificaciones`

### Servicios de Infraestructura

#### Eureka Server
- **URL**: `http://localhost:32011` (vía NodePort)
- **Dashboard**: Interfaz web para monitorear servicios registrados

#### RabbitMQ Management
- **URL**: `http://localhost:32672` (vía NodePort)
- **Usuario**: `admin`
- **Contraseña**: `admin`

## Comandos Útiles

### Monitoreo
```bash
# Ver logs de un servicio específico
kubectl logs -n aplicacion-distribuida deployment/api-gateway

# Seguir logs en tiempo real
kubectl logs -f -n aplicacion-distribuida deployment/ms-autorizacion

# Describir un pod
kubectl describe pod -n aplicacion-distribuida <nombre-del-pod>
```

### Escalado
```bash
# Escalar un servicio
kubectl scale deployment ms-publicaciones --replicas=3 -n aplicacion-distribuida
```

### Reinicio
```bash
# Reiniciar un deployment
kubectl rollout restart deployment/api-gateway -n aplicacion-distribuida
```

### Port Forward (para testing directo)
```bash
# Acceso directo a un servicio
kubectl port-forward -n aplicacion-distribuida svc/ms-autorizacion 8080:8080
```

## Limpieza del Entorno

### Eliminar Todos los Recursos
```bash
kubectl delete -f . -n aplicacion-distribuida
```

### Eliminar Namespace
```bash
kubectl delete namespace aplicacion-distribuida
```

### Detener Minikube
```bash
minikube stop
```

## Solución de Problemas

### Pod en CrashLoopBackOff
```bash
# Ver logs para identificar el problema
kubectl logs -n aplicacion-distribuida <nombre-del-pod>

# Verificar eventos
kubectl get events -n aplicacion-distribuida --sort-by='.lastTimestamp'
```

### Ingress no accesible
```bash
# Verificar estado del ingress
kubectl describe ingress api-gateway-ingress -n aplicacion-distribuida

# Verificar pods del ingress controller
kubectl get pods -n ingress-nginx
```

### Health Checks fallando
Los health checks están temporalmente deshabilitados para evitar conflictos con el filtro JWT. Si necesitas habilitarlos:

1. Configurar el filtro JWT para permitir `/actuator/**`
2. Descomentar las secciones de `livenessProbe` y `readinessProbe` en los YAMLs
3. Aplicar los cambios: `kubectl apply -f <archivo>.yml`

## Configuración de Bases de Datos

### PostgreSQL
- **Host**: `postgres:5432`
- **Bases de datos creadas automáticamente**:
  - `autorizacion_db`
  - `publicaciones_db`
  - `catalogo_db`
  - `notifications`
- **Usuario**: `admin`
- **Contraseña**: `admin12345`

### RabbitMQ
- **Host**: `rabbitmq:5672`
- **Usuario**: `admin`
- **Contraseña**: `admin`

## Variables de Entorno Configuradas

Todas las variables necesarias están configuradas en los archivos YAML:
- Conexiones a base de datos
- URLs de Eureka Server
- Configuración de RabbitMQ
- Configuración de JWT
- Configuración de memoria JVM

## Testing con Postman

Importar la colección `postman.json` que contiene todos los endpoints configurados para trabajar con `http://oppadev.io`.

---

**Nota**: Esta configuración está optimizada para desarrollo local usando Minikube. Para producción, se requieren ajustes adicionales de seguridad, persistencia y escalabilidad.