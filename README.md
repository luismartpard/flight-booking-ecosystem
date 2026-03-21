# ✈️ Flight Booking Ecosystem

Backend ecosystem for a flight booking platform built with **Java 21** and **Spring Boot 4.0.3**, following **Hexagonal Architecture (Ports & Adapters)** and modern backend best practices.

---

# 🇪🇸 Español

## 📦 Descripción

Este proyecto es un ecosistema backend diseñado para una plataforma de reservas de vuelos, construido con un enfoque **hexagonal (Ports & Adapters)** para garantizar **desacoplamiento, mantenibilidad y escalabilidad**.

Actualmente incluye:

* ✅ Servicio de geografía (países y continentes)
* ✅ Módulo común compartido
* ✅ Persistencia con PostgreSQL + PostGIS
* ✅ Migraciones de base de datos con Flyway
* ✅ Contenerización con Docker
* ✅ Análisis de código con SonarQube
* 🚧 Próximamente: City, Airport, Flight, Booking

---

## 🧱 Arquitectura

El proyecto sigue **Arquitectura Hexagonal**.

### Principios aplicados

* Separación clara entre dominio, aplicación e infraestructura
* Dominio independiente de frameworks
* Uso de **puertos (interfaces)** y **adaptadores**
* Controladores como **entrypoints (adaptadores de entrada)**
* Persistencia desacoplada mediante repositorios

---

## ⚙️ Stack tecnológico

### Core

* Java 21
* Spring Boot 4.0.3
* Maven

### Persistencia

* PostgreSQL
* PostGIS
* Spring Data JPA / Hibernate

### Base de datos

* Flyway (control de versiones y migraciones)

### DevOps

* Docker
* Docker Compose

### Calidad de código

* SonarQube

### Testing

* JUnit 5
* Mockito
* AssertJ

---

## 🗄️ Migraciones con Flyway

El proyecto utiliza **Flyway** para versionar la base de datos.

* Las migraciones se ejecutan automáticamente al arrancar la aplicación
* Se garantiza consistencia entre entornos
* Versionado incremental de cambios en la base de datos

Ejemplo:

```sql
V1__create_country_table.sql
V2__add_continent.sql
```

---

## 🚀 Ejecución del proyecto

### 1. Clonar repositorio

```bash
git clone https://github.com/luismartpard/flight-booking-ecosystem.git
cd flight-booking-ecosystem
```

---

### 2. Configuración de entorno

Crear un archivo `.env` en la raíz del proyecto:

```env
DB_URL=jdbc:postgresql://postgres:5432/geography
DB_USER=geography
DB_PASSWORD=geography
DB_PORT=5433
```

> ⚠️ El archivo `.env` no se sube al repositorio.
> Usar `.env.example` como referencia.

---

### 3. Ejecutar con Docker

```bash
docker-compose up --build
```

---

## 🐳 Servicios disponibles

* API → http://localhost:8080
* SonarQube → http://localhost:9000
* PostgreSQL → localhost:5433

---

## 🌍 Geography Service

Responsabilidades principales:

* Gestión de países y continentes
* Paginación y ordenación
* Validación de parámetros
* Lógica de dominio desacoplada

---

## 🔌 Arquitectura (Ports & Adapters)

* Los **controladores REST** actúan como adaptadores de entrada
* Los **repositorios** son puertos definidos en dominio
* Las implementaciones JPA son adaptadores de infraestructura
* El dominio no depende de Spring

---

## 🧪 Testing

Estrategia de testing:

* Tests unitarios en dominio (sin Spring)
* Tests de aplicación (casos de uso)
* Uso de Mockito para mocks
* AssertJ para aserciones

---

## 🔍 Calidad de código

Se incluye SonarQube para:

* Análisis estático
* Code smells
* Cobertura de tests

Acceso:

http://localhost:9000
Usuario: `admin`
Contraseña: `admin`

---

## 🔐 Configuración

* `.env` → configuración local (no versionado)
* `.env.example` → plantilla para desarrolladores

---

## 🛣️ Roadmap

* [ ] City
* [ ] Airport
* [ ] Flight
* [ ] Booking
* [ ] Autenticación (JWT)
* [ ] Despliegue en AWS
* [ ] Observabilidad (logs, métricas)

---

## 🎯 Objetivo del proyecto

Construir un backend:

* Escalable
* Mantenible
* Desacoplado
* Preparado para entornos reales

---

# 🇬🇧 English

## 📦 Overview

This project is a backend ecosystem for a flight booking platform built using **Hexagonal Architecture (Ports & Adapters)** to ensure **decoupling, maintainability and scalability**.

Current features:

* ✅ Geography service (countries & continents)
* ✅ Shared common module
* ✅ PostgreSQL + PostGIS persistence
* ✅ Database migrations with Flyway
* ✅ Dockerized infrastructure
* ✅ SonarQube integration
* 🚧 Upcoming modules: City, Airport, Flight, Booking

---

## 🧱 Architecture

The project follows **Hexagonal Architecture**.

### Key principles

* Clear separation between domain, application and infrastructure
* Framework-independent domain
* Use of **ports (interfaces)** and **adapters**
* Controllers as **entrypoints (driving adapters)**
* Decoupled persistence layer

---

## ⚙️ Tech Stack

### Core

* Java 21
* Spring Boot 4.0.3
* Maven

### Persistence

* PostgreSQL
* PostGIS
* Spring Data JPA / Hibernate

### Database

* Flyway (versioned migrations)

### DevOps

* Docker
* Docker Compose

### Code Quality

* SonarQube

### Testing

* JUnit 5
* Mockito
* AssertJ

---

## 🗄️ Database Migrations (Flyway)

The project uses **Flyway** for database versioning.

* Migrations run automatically at startup
* Ensures consistency across environments
* Incremental version control

Example:

```sql
V1__create_country_table.sql
V2__add_continent.sql
```

---

## 🚀 Run the project

```bash
docker-compose up --build
```

---

## 🐳 Services

* API → http://localhost:8080
* SonarQube → http://localhost:9000
* PostgreSQL → localhost:5433

---

## 🌍 Geography Service

Main responsibilities:

* Manage countries and continents
* Pagination and sorting
* Input validation
* Domain-driven logic

---

## 🔌 Ports & Adapters

* REST controllers act as entrypoints
* Repositories are domain ports
* JPA implementations are infrastructure adapters
* Domain layer is framework-independent

---

## 🧪 Testing Strategy

* Domain → unit tests (no Spring)
* Application → use case tests
* Infrastructure → integration tests

---

## 🔍 Code Quality

SonarQube is included for:

* Static analysis
* Code smells detection
* Coverage tracking

---

## 🔐 Configuration

* `.env` → local configuration (ignored)
* `.env.example` → template (committed)

---

## 🛣️ Roadmap

* City module
* Airport module
* Flight module
* Booking system
* Authentication (JWT)
* AWS deployment
* Observability

---

## 👨‍💻 Author

Luis Martínez
Backend Developer (Java / Spring Boot)
