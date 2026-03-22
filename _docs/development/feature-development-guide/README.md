# Feature Development Guide

## 1. Objective

Define the purpose of the feature or module.

This section explains what is being built and why, including the business problem it solves and the expected outcome.

It should answer:

- What is the goal of this feature?
- What business value does it provide?
- Who or what system will use it?


## 2. Scope

Define the boundaries of the feature.

This section specifies what is included and what is explicitly excluded from the implementation to avoid ambiguity and scope creep.

It should include:

- Functionalities covered by the feature
- Functionalities not included
- Assumptions and constraints (if any)


## 3. Architecture Overview

Provide a high-level description of the system design.

This section explains how the different parts of the system are structured and interact with each other.


### 3.1 Architectural Style

Describe the architectural approach used in the project.

This project follows a layered architecture inspired by Hexagonal Architecture (Ports and Adapters).

Key characteristics:

- Clear separation between domain, application, and infrastructure
- Domain is independent of frameworks and external technologies
- Communication between layers is done through well-defined interfaces (ports)
- Infrastructure depends on domain, never the opposite


### 3.2 Layer Responsibilities

Define the responsibility of each layer in the system.

- Domain Layer:
    - Contains the core business logic and entities
    - Framework-independent
    - Defines repository interfaces (ports)

- Application Layer:
    - Implements use cases
    - Orchestrates domain logic
    - Handles input/output through DTOs

- Infrastructure Layer:
    - Implements technical details (JPA, REST controllers, external APIs)
    - Contains adapters for persistence and external systems
    - Depends on domain and application layers

## 4. Development Flow
### 4.1 Start with the Domain Model

The first step when developing a new feature is to define the domain model.

The domain model represents the core business concept and must be implemented
before any infrastructure, persistence, or API layer.

---

#### 1. Purpose

1.1 The domain model defines the business concept of the feature  
1.2 It represents the core data and rules of the system  
1.3 It is the foundation for all other layers

---

#### 2. Responsibilities

2.1 Represent the business concept (e.g., Airline, Country, Airport)  
2.2 Define the core attributes of the entity  
2.3 Define the entity identity (natural or business key)  
2.4 Remain independent of any framework  
2.5 Be immutable whenever possible

---

#### 3. Implementation Rules

3.1 Place the model under `domain/model`  
3.2 Use plain Java classes (POJOs)  
3.3 All fields must be `private` and `final`  
3.4 Provide a constructor with all required fields  
3.5 Provide getters only (no setters)

---

#### 4. Restrictions

4.1 Do not include database identifiers (e.g., `id`)  
4.2 Do not include audit fields (`createdAt`, `updatedAt`)  
4.3 Do not include framework annotations (`@Entity`, `@Service`, etc.)  
4.4 Do not include persistence logic  
4.5 Do not include API logic or DTOs

---

#### 5. Identity

5.1 Each entity must define a business identity  
5.2 The identity must be unique and stable over time  
5.3 The identity must be used in equality comparisons  
5.4 Technical identifiers must not be used in the domain

---

#### 6. Immutability

6.1 The domain model should be immutable  
6.2 State must be defined at construction time  
6.3 No setters should be exposed  
6.4 Changes should be represented by creating new instances

---

#### 7. Persistence Independence

7.1 The domain model must not assume how data is stored  
7.2 It must be independent from any database or ORM  
7.3 It must be usable in memory without modification  
7.4 Persistence concerns belong to the infrastructure layer

---

#### 8. Optional Elements

8.1 The domain may include enums when required  
8.2 The domain may include value objects for complex concepts  
8.3 These elements are optional and depend on the feature

---

#### 9. Why Start with the Domain

9.1 Ensures the business is clearly defined before technical decisions  
9.2 Prevents coupling with frameworks  
9.3 Enforces separation of concerns  
9.4 Makes the system easier to test and evolve

---

#### 10. Common Mistakes

10.1 Starting from the database design (JPA first)  
10.2 Adding framework annotations in the domain  
10.3 Using DTOs as domain models  
10.4 Mixing persistence logic inside the domain  
10.5 Not defining a clear business identity
### 4.2 Define Repository Ports

After defining the domain model, the next step is to define the repository ports.

A repository port represents the contract between the application layer and the persistence layer.
It defines the operations required by the application without specifying how they are implemented.

---

#### 1. Purpose

1.1 Define how the application interacts with persistence  
1.2 Decouple the application layer from infrastructure  
1.3 Enable multiple implementations (JPA, in-memory, external services)  
1.4 Enforce dependency inversion

---

#### 2. Responsibilities

2.1 Provide operations to persist domain models  
2.2 Retrieve domain models by their business identity  
2.3 Check existence of entities  
2.4 Support querying and filtering when required

---

#### 3. Location

3.1 Repository ports must be placed under `application/port/out`  
3.2 They belong to the application layer  
3.3 They must not depend on infrastructure

---

#### 4. Design Rules

4.1 Define only the operations required by the use cases  
4.2 Keep interfaces small and focused  
4.3 Use business-oriented method names  
4.4 Do not expose persistence details  
4.5 Do not assume any specific database

---

#### 5. Data Types

5.1 Repository ports must work with domain models only  
5.2 They must not expose JPA entities or database models  
5.3 Return types must be domain-friendly (e.g., Optional, List)  
5.4 Never return null

---

#### 6. Framework Independence

6.1 Do not use framework-specific types (e.g., Spring Pageable)  
6.2 Do not include annotations (`@Repository`)  
6.3 Do not depend on JPA, Hibernate, or any ORM

---

#### 7. Abstraction Level

7.1 The repository port defines WHAT the application needs  
7.2 It does not define HOW the data is retrieved or stored  
7.3 Implementation details belong to the infrastructure layer

---

#### 8. Optional Operations

8.1 Pagination may be included if required  
8.2 Filtering or search operations may be added  
8.3 These operations must remain domain-oriented

---

#### 9. Why This Step Is Important

9.1 Enforces a clean separation between application and infrastructure  
9.2 Improves testability (easy to mock)  
9.3 Allows changing persistence without affecting business logic  
9.4 Keeps the architecture flexible and maintainable

---

#### 10. Common Mistakes

10.1 Using JPA entities in repository ports  
10.2 Returning framework-specific types  
10.3 Adding annotations or persistence logic  
10.4 Defining too many unnecessary methods  
10.5 Designing repositories based on the database instead of use cases
### 4.3 Implement Use Cases

After defining the repository ports, the next step is to implement the use cases.

A use case represents a specific business action or operation.
It defines how the system behaves from the perspective of the client.

---

#### 1. Purpose

1.1 Define the behavior of the application  
1.2 Orchestrate interactions between domain and repository ports  
1.3 Encapsulate business logic and rules  
1.4 Act as the entry point to the application layer

---

#### 2. Responsibilities

2.1 Execute a single business operation  
2.2 Coordinate calls to repository ports  
2.3 Apply validations and business rules  
2.4 Return domain models or response data

---

#### 3. Structure

3.1 Each use case must be defined as an interface (input port)  
3.2 The implementation must be placed in `application/usecase`  
3.3 The implementation must depend only on ports, not on infrastructure

---

#### 4. Design Rules

4.1 Define one use case per action  
4.2 Use clear and intention-revealing names  
4.3 Keep use cases small and focused  
4.4 Avoid combining multiple responsibilities in a single use case  
4.5 Do not include infrastructure logic

---

#### 5. Dependencies

5.1 Use cases depend on repository ports (outbound ports)  
5.2 Dependencies must be injected through the constructor  
5.3 The use case must never instantiate its dependencies directly  
5.4 The use case must depend only on abstractions (interfaces)  
5.5 The repository port defines the boundary between application and infrastructure

---

#### 6. Input and Output

6.1 Use cases may receive:
- primitive values (e.g., identifiers)
- command/query objects for complex inputs

6.2 Use cases may return:
- domain models
- optional results
- collections

6.3 Use cases must never return null

---

#### 7. Framework Independence

7.1 Do not include framework annotations (`@Service`, etc.)  
7.2 Do not depend on Spring or any framework  
7.3 The application layer must remain framework-agnostic

---

#### 8. Interaction Flow

8.1 Receive input from the caller (e.g., controller)  
8.2 Validate input if necessary  
8.3 Call repository ports  
8.4 Apply business logic  
8.5 Return the result

---

#### 9. Why This Step Is Important

9.1 Defines the behavior of the system  
9.2 Keeps business logic out of controllers  
9.3 Ensures a clean separation of concerns  
9.4 Makes the application easy to test

---

#### 10. Common Mistakes

10.1 Putting business logic in controllers  
10.2 Accessing persistence directly without ports  
10.3 Creating large, generic services with multiple responsibilities  
10.4 Mixing use cases with infrastructure logic  
10.5 Returning null instead of proper domain-friendly types
### 4.4 Implement Persistence Layer

After implementing the use cases, the next step is to implement the persistence layer.

The persistence layer is responsible for storing and retrieving data from the database.
It provides the concrete implementation of the repository ports defined in the application layer.

---

#### 1. Purpose

1.1 Represent how data is stored in the database  
1.2 Implement the persistence model required by JPA  
1.3 Provide the technical components needed to persist and retrieve data  
1.4 Keep persistence concerns isolated from the domain model

---

#### 2. Persistence Entity

2.1 Each feature must define a JPA entity that represents the database structure  
2.2 JPA entities must be placed under `infrastructure/out/persistence/entity`  
2.3 A JPA entity is a persistence model, not a domain model  
2.4 It may include technical fields required by the database and ORM

---

#### 3. Responsibilities of the JPA Entity

3.1 Map the table and columns used in the database  
3.2 Define the technical identifier used by persistence  
3.3 Include audit fields when required (e.g., `createdAt`, `updatedAt`)  
3.4 Define database constraints such as length, nullability, and uniqueness  
3.5 Support ORM lifecycle requirements

---

#### 4. Design Rules

4.1 JPA entities may contain technical identifiers such as `id`  
4.2 JPA entities may contain audit fields managed by the database or persistence layer  
4.3 JPA entities must include JPA annotations such as `@Entity`, `@Table`, `@Id`, and `@Column`  
4.4 JPA entities must not be used as domain models  
4.5 JPA entities must remain inside the infrastructure layer

---

#### 5. Equality

5.1 Equality in JPA entities must be based on the persistence identity  
5.2 The technical identifier is the reference for equality once the entity has been persisted  
5.3 JPA entity equality must not be mixed with domain identity rules

---

#### 6. Separation from the Domain

6.1 The domain model represents the business concept  
6.2 The JPA entity represents the database structure  
6.3 These two models have different responsibilities and must remain separated  
6.4 Technical persistence concerns must never leak into the domain layer

---

#### 7. Why This Step Is Important

7.1 Keeps database concerns isolated from business logic  
7.2 Allows the domain model to remain clean and framework-independent  
7.3 Supports ORM requirements without polluting the application layer  
7.4 Makes persistence implementation explicit and maintainable

#### 8. Spring Data Repository

8.1 The persistence layer must define a repository interface using Spring Data JPA

8.2 This repository is responsible for interacting directly with the database

8.3 It must be placed under `infrastructure/out/persistence/repository`

---

#### 9. Responsibilities of the Repository

9.1 Execute database operations (CRUD)  
9.2 Provide query methods based on persistence needs  
9.3 Work exclusively with JPA entities

---

#### 10. Design Rules

10.1 The repository must extend `JpaRepository` (or similar)  
10.2 It must operate only on JPA entities, never on domain models  
10.3 It may define query methods using Spring Data conventions  
10.4 It must not contain business logic  
10.5 It must not be exposed outside the infrastructure layer

---

#### 11. Separation from the Application Layer

11.1 The application layer must not depend on this repository directly  
11.2 The repository must not implement business use cases  
11.3 It is an internal infrastructure component

---

#### 12. Why This Step Is Important

12.1 Encapsulates database access logic  
12.2 Leverages Spring Data for persistence operations  
12.3 Keeps persistence details isolated  
12.4 Prepares the foundation for the persistence adapter

#### 13. Persistence Adapter

13.1 The persistence layer must define an adapter that implements the repository port

13.2 The adapter must be placed under `infrastructure/out/persistence/adapter`

13.3 The adapter is the bridge between the application layer and the persistence layer

13.4 It provides the concrete implementation of the operations declared in the repository port

---

#### 14. Responsibilities of the Adapter

14.1 Implement the outbound port defined in `application/port/out`

14.2 Delegate database access to the Spring Data repository

14.3 Convert between domain models and JPA entities

14.4 Keep persistence details isolated from the application layer

---

#### 15. Design Rules

15.1 The adapter must implement the corresponding repository port

15.2 The adapter must depend on the Spring Data repository

15.3 The adapter must not expose JPA entities outside the infrastructure layer

15.4 The adapter must return domain models, not persistence models

15.5 The adapter must not contain business logic

---

#### 16. Dependency Flow

16.1 The use case depends on the repository port

16.2 The repository port is implemented by the persistence adapter

16.3 The persistence adapter depends on the Spring Data repository

16.4 The Spring Data repository works with JPA entities and the database

---

#### 17. Why This Step Is Important

17.1 It completes the contract defined in the application layer

17.2 It keeps the use cases independent from JPA and Spring Data

17.3 It centralizes persistence-related implementation details

17.4 It preserves the separation between domain, application, and infrastructure

#### 18. Specifications (Optional)

18.1 Some features may require dynamic or composable query criteria

18.2 In those cases, specifications can be implemented under `infrastructure/out/persistence/specification`

18.3 Specifications are part of the persistence layer and must work with JPA entities, not domain models

18.4 Each specification must represent a persistence query condition

18.5 Specifications should be small, focused, and reusable

---

#### 19. Responsibilities of Specifications

19.1 Encapsulate dynamic filtering logic

19.2 Allow combining multiple query conditions

19.3 Keep query construction outside the adapter and repository implementation logic

19.4 Improve readability and maintainability for complex searches

---

#### 20. Integration with the Repository

20.1 When specifications are required, the Spring Data repository must also extend `JpaSpecificationExecutor`

20.2 This enables the execution of dynamic queries based on specifications

20.3 The persistence adapter may then combine and use these specifications to support filtering and search use cases

---

#### 21. Design Rules

21.1 Specifications are optional and should only be introduced when needed

21.2 They must remain in the infrastructure layer

21.3 They must not contain business logic

21.4 They must not be exposed directly to the application layer

21.5 They must operate only on persistence attributes and JPA entity relationships

---

#### 22. Why This Step Is Important

22.1 Keeps complex query logic isolated from use cases

22.2 Avoids bloated repository method definitions

22.3 Supports flexible and maintainable filtering mechanisms

22.4 Preserves the separation between application logic and persistence concerns

### 4.5 Implement Mappers

After implementing the persistence layer, the next step is to implement the mappers.

Mappers are responsible for transforming data between the different layers of the application.

They allow each layer to work with its own models while preserving a clear separation of concerns.

---

#### 1. Purpose

1.1 Transform data between layers

1.2 Keep domain, persistence, and API models separated

1.3 Centralize mapping logic in dedicated components

1.4 Prevent implementation details from leaking across layers

---

#### 2. Types of Mappers

2.1 Persistence mappers
- Convert between JPA entities and domain models
- Are used by the persistence adapter
- Must be placed under `infrastructure/out/persistence/mapper`

2.2 Web mappers
- Convert between domain models and DTOs
- Are used by controllers
- Must be placed under `infrastructure/in/web/mapper`

---

#### 3. Responsibilities

3.1 Persistence mappers must transform persistence models into domain models

3.2 Persistence mappers must transform domain models into persistence models when needed

3.3 Web mappers must transform domain models into response DTOs

3.4 Web mappers may transform request DTOs into command/query objects or domain-related input structures when needed

3.5 Mappers must only handle data transformation

---

#### 4. Design Rules

4.1 Mappers must not contain business logic

4.2 Mappers must not access repositories or external systems

4.3 Mappers must remain small and focused

4.4 Each mapper must have a clear and limited responsibility

4.5 Mapping logic must not be duplicated in controllers, use cases, or adapters

---

#### 5. Separation of Mappers

5.1 Persistence mappers and web mappers are both part of the mapping layer, but they serve different purposes

5.2 They must be explained together as part of the same step

5.3 They must be implemented in different packages according to their layer

5.4 They must not be mixed in the same package or component

5.5 Each mapper must operate only within its own layer boundaries

---

#### 6. Framework Usage

6.1 MapStruct may be used for web mappers or persistence mappers when appropriate

6.2 Manual mappers may also be used when the transformation is simple or more explicit control is preferred

6.3 The chosen approach must keep the mapping logic isolated and maintainable

---

#### 7. Why This Step Is Important

7.1 Prevents model leakage between layers

7.2 Preserves a clean separation of concerns

7.3 Makes transformations explicit and reusable

7.4 Improves readability and maintainability of the codebase

---

#### 8. Common Mistakes

8.1 Returning JPA entities directly from the API

8.2 Using DTOs as domain models

8.3 Placing mapping logic inside controllers or use cases

8.4 Mixing persistence and web mapping responsibilities in the same component

8.5 Adding business rules inside mappers
### 4.6 Define DTOs

After implementing the mappers, the next step is to define the DTOs.

DTOs (Data Transfer Objects) represent the data exposed by the API.
They define the structure of requests and responses exchanged with external clients.

---

#### 1. Purpose

1.1 Define the API contract exposed to clients

1.2 Separate API models from domain models

1.3 Control which data is received and returned by the system

1.4 Prevent internal models from being exposed directly

---

#### 2. Responsibilities

2.1 Represent request and response payloads

2.2 Expose only the fields required by the API contract

2.3 Remain independent from persistence models

2.4 Avoid leaking internal implementation details

---

#### 3. Location

3.1 DTOs must be placed under `infrastructure/in/web/dto`

3.2 They belong to the inbound web layer

3.3 They must not be placed in the domain or application layers

---

#### 4. Design Rules

4.1 DTOs must be simple data carriers

4.2 DTOs must not contain business logic

4.3 DTOs must not contain persistence annotations

4.4 DTOs must not be used as domain models

4.5 DTOs should include only the fields required by the API

---

#### 5. Request and Response DTOs

5.1 Request DTOs represent the data received from clients

5.2 Response DTOs represent the data returned to clients

5.3 Request and response DTOs may differ depending on the use case

5.4 DTO design must be driven by the API contract, not by the database structure

---

#### 6. Implementation Style

6.1 DTOs may be implemented as Java records when appropriate

6.2 Records are suitable when the DTO is immutable and only carries data

6.3 Simpler DTO definitions improve readability and reduce boilerplate

---

#### 7. Separation from Other Models

7.1 DTOs are not domain models

7.2 DTOs are not JPA entities

7.3 DTOs must remain isolated from business and persistence concerns

7.4 Conversion between DTOs and domain models must be handled by web mappers

---

#### 8. Why This Step Is Important

8.1 Defines a clear external contract for the API

8.2 Prevents exposure of internal models

8.3 Keeps the API layer independent from the domain and persistence layers

8.4 Makes request and response structures explicit and maintainable

---

#### 9. Common Mistakes

9.1 Using domain models directly in controllers

9.2 Returning JPA entities in API responses

9.3 Adding business logic inside DTOs

9.4 Designing DTOs based on database tables instead of API needs

9.5 Reusing the same DTO for unrelated operations without clear intent

### 4.7 Configure Beans

After implementing the use cases, the next step is to configure the application beans.

This step is responsible for wiring together the application components and providing
the concrete implementations required at runtime.

---

#### 1. Purpose

1.1 Connect use cases with their dependencies

1.2 Provide concrete implementations for repository ports

1.3 Assemble the application using dependency injection

1.4 Keep the application layer independent from the framework

---

#### 2. Responsibilities

2.1 Instantiate use case implementations

2.2 Inject repository ports into use cases

2.3 Define how components are connected

2.4 Delegate dependency resolution to the framework

---

#### 3. Location

3.1 Configuration classes must be placed under `infrastructure/config`

3.2 They belong to the infrastructure layer

3.3 They must not be placed in the domain or application layers

---

#### 4. Design Rules

4.1 Use configuration classes to define application beans

4.2 Use bean definitions to instantiate use cases

4.3 Inject dependencies through method parameters

4.4 Do not instantiate dependencies manually inside use cases

4.5 Do not include business logic in configuration classes

---

#### 5. Dependency Flow

5.1 Use cases depend on repository ports

5.2 Repository ports are implemented by persistence adapters

5.3 Configuration classes connect use cases with their implementations

5.4 The framework resolves and injects dependencies at runtime

---

#### 6. Framework Isolation

6.1 The application layer must remain free of framework annotations

6.2 Framework-specific configuration must be isolated in the infrastructure layer

6.3 Dependency injection must be handled externally to the use cases

---

#### 7. Why This Step Is Important

7.1 Keeps the application layer framework-independent

7.2 Centralizes object creation and wiring

7.3 Enforces dependency injection principles

7.4 Improves maintainability and testability

---

#### 8. Common Mistakes

8.1 Instantiating dependencies inside use cases

8.2 Adding framework annotations inside the application layer

8.3 Mixing configuration logic with business logic

8.4 Bypassing ports and directly wiring infrastructure components

### 4.8 Expose API (Controllers)

After configuring the application beans, the next step is to expose the API through controllers.

Controllers are the entry point of the web layer.
They receive HTTP requests, validate input, invoke use cases, and return HTTP responses.

---

#### 1. Purpose

1.1 Expose the application functionality through HTTP endpoints

1.2 Receive requests from external clients

1.3 Delegate business execution to use cases

1.4 Return structured HTTP responses

---

#### 2. Responsibilities

2.1 Define API endpoints

2.2 Validate incoming input

2.3 Call the appropriate use case

2.4 Transform domain models into response DTOs through web mappers

2.5 Return the corresponding HTTP response

---

#### 3. Location

3.1 Controllers must be placed under `infrastructure/in/web`

3.2 They belong to the inbound web layer

3.3 They must not be placed in the domain or application layers

---

#### 4. Design Rules

4.1 Controllers must remain thin

4.2 Controllers must not contain business logic

4.3 Controllers must depend on input ports (use cases), not on persistence components

4.4 Controllers must use DTOs for request and response payloads

4.5 Controllers must use web mappers to transform domain models into DTOs

---

#### 5. Input Validation

5.1 Input validation must be performed at the API boundary

5.2 Validation annotations may be used for path variables, query parameters, and request bodies

5.3 Invalid input must be rejected before reaching the business logic

5.4 Validation rules must be aligned with the API contract

---

#### 6. Dependency Rules

6.1 Controllers must depend on use cases defined in `application/port/in`

6.2 Controllers must not access repository ports directly

6.3 Controllers must not access JPA repositories directly

6.4 Controllers may depend on web mappers for response transformation

---

#### 7. Response Handling

7.1 Controllers must return HTTP-oriented responses

7.2 Domain models must not be returned directly to external clients

7.3 Response DTOs must define the API output structure

7.4 HTTP status codes must reflect the result of the operation

---

#### 8. Separation of Concerns

8.1 Controllers handle HTTP concerns

8.2 Use cases handle application behavior and business flow

8.3 Persistence adapters handle data access

8.4 Mappers handle data transformation between layers

---

#### 9. Why This Step Is Important

9.1 Exposes the application in a controlled and explicit way

9.2 Keeps HTTP concerns isolated from business logic

9.3 Preserves the dependency flow of the architecture

9.4 Makes the API contract clear and maintainable

---

#### 10. Common Mistakes

10.1 Adding business logic inside controllers

10.2 Accessing repositories directly from controllers

10.3 Returning domain models or JPA entities directly in responses

10.4 Performing data transformation manually inside controllers

10.5 Mixing HTTP concerns with persistence concerns