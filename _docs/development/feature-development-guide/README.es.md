# Feature Development Guide

## 1. Objetivo

Definir el propósito de la funcionalidad o módulo.

Esta sección explica qué se va a construir y por qué, incluyendo el problema de negocio que resuelve y el resultado esperado.

Debe responder a:

- ¿Cuál es el objetivo de esta funcionalidad?
- ¿Qué valor de negocio aporta?
- ¿Qué usuario o sistema la utilizará?

## 2. Alcance

Definir los límites de la funcionalidad.

Esta sección especifica qué está incluido y qué queda explícitamente fuera de la implementación para evitar ambigüedad y crecimiento descontrolado del alcance.

Debe incluir:

- Funcionalidades cubiertas por la funcionalidad
- Funcionalidades no incluidas
- Suposiciones y restricciones (si existen)

## 3. Visión General de la Arquitectura

Proporcionar una descripción de alto nivel del diseño del sistema.

Esta sección explica cómo están estructuradas e interactúan las diferentes partes del sistema.

### 3.1 Estilo Arquitectónico

Describir el enfoque arquitectónico utilizado en el proyecto.

Este proyecto sigue una arquitectura en capas inspirada en la Arquitectura Hexagonal (Puertos y Adaptadores).

Características principales:

- Separación clara entre dominio, aplicación e infraestructura
- El dominio es independiente de frameworks y tecnologías externas
- La comunicación entre capas se realiza mediante interfaces bien definidas (puertos)
- La infraestructura depende del dominio, nunca al contrario

### 3.2 Responsabilidades de las Capas

Definir la responsabilidad de cada capa en el sistema.

- **Capa de Dominio:**
  - Contiene la lógica de negocio principal y las entidades
  - Es independiente del framework
  - Define las interfaces de repositorio (puertos)

- **Capa de Aplicación:**
  - Implementa los casos de uso
  - Orquesta la lógica de dominio
  - Gestiona la entrada y salida mediante DTOs

- **Capa de Infraestructura:**
  - Implementa los detalles técnicos (JPA, controladores REST, APIs externas)
  - Contiene adaptadores para persistencia y sistemas externos
  - Depende de las capas de dominio y aplicación

## 4. Flujo de Desarrollo

### 4.1 Comenzar con el Modelo de Dominio

El primer paso al desarrollar una nueva funcionalidad es definir el modelo de dominio.

El modelo de dominio representa el concepto de negocio principal y debe implementarse antes que cualquier capa de infraestructura, persistencia o API.

---

#### 1. Propósito

1.1 El modelo de dominio define el concepto de negocio de la funcionalidad  
1.2 Representa los datos y reglas principales del sistema  
1.3 Es la base para todas las demás capas

---

#### 2. Responsabilidades

2.1 Representar el concepto de negocio (por ejemplo: Airline, Country, Airport)  
2.2 Definir los atributos principales de la entidad  
2.3 Definir la identidad de la entidad (natural o de negocio)  
2.4 Permanecer independiente de cualquier framework  
2.5 Ser inmutable siempre que sea posible

---

#### 3. Reglas de Implementación

3.1 Colocar el modelo en `domain/model`  
3.2 Usar clases Java simples (POJOs)  
3.3 Todos los campos deben ser `private` y `final`  
3.4 Proporcionar un constructor con todos los campos requeridos  
3.5 Proporcionar solo getters (sin setters)

---

#### 4. Restricciones

4.1 No incluir identificadores de base de datos (por ejemplo, `id`)  
4.2 No incluir campos de auditoría (`createdAt`, `updatedAt`)  
4.3 No incluir anotaciones de framework (`@Entity`, `@Service`, etc.)  
4.4 No incluir lógica de persistencia  
4.5 No incluir lógica de API ni DTOs

---

#### 5. Identidad

5.1 Cada entidad debe definir una identidad de negocio  
5.2 La identidad debe ser única y estable en el tiempo  
5.3 La identidad debe utilizarse en las comparaciones de igualdad  
5.4 Los identificadores técnicos no deben utilizarse en el dominio

---

#### 6. Inmutabilidad

6.1 El modelo de dominio debe ser inmutable  
6.2 El estado debe definirse en el momento de la construcción  
6.3 No deben exponerse setters  
6.4 Los cambios deben representarse creando nuevas instancias

---

#### 7. Independencia de la Persistencia

7.1 El modelo de dominio no debe asumir cómo se almacenan los datos  
7.2 Debe ser independiente de cualquier base de datos u ORM  
7.3 Debe poder usarse en memoria sin modificaciones  
7.4 Las preocupaciones de persistencia pertenecen a la capa de infraestructura

---

#### 8. Elementos Opcionales

8.1 El dominio puede incluir enums cuando sea necesario  
8.2 El dominio puede incluir value objects para conceptos complejos  
8.3 Estos elementos son opcionales y dependen de la funcionalidad

---

#### 9. Por Qué Empezar por el Dominio

9.1 Asegura que el negocio esté claramente definido antes de tomar decisiones técnicas  
9.2 Evita el acoplamiento con frameworks  
9.3 Refuerza la separación de responsabilidades  
9.4 Hace que el sistema sea más fácil de probar y evolucionar

---

#### 10. Errores Comunes

10.1 Empezar por el diseño de la base de datos (JPA primero)  
10.2 Añadir anotaciones de framework en el dominio  
10.3 Usar DTOs como modelos de dominio  
10.4 Mezclar lógica de persistencia dentro del dominio  
10.5 No definir una identidad de negocio clara

### 4.2 Definir los Puertos de Repositorio

Después de definir el modelo de dominio, el siguiente paso es definir los puertos de repositorio.

Un puerto de repositorio representa el contrato entre la capa de aplicación y la capa de persistencia.  
Define las operaciones requeridas por la aplicación sin especificar cómo se implementan.

---

#### 1. Propósito

1.1 Definir cómo interactúa la aplicación con la persistencia  
1.2 Desacoplar la capa de aplicación de la infraestructura  
1.3 Permitir múltiples implementaciones (JPA, en memoria, servicios externos)  
1.4 Aplicar inversión de dependencias

---

#### 2. Responsabilidades

2.1 Proporcionar operaciones para persistir modelos de dominio  
2.2 Recuperar modelos de dominio por su identidad de negocio  
2.3 Comprobar la existencia de entidades  
2.4 Soportar consultas y filtros cuando sea necesario

---

#### 3. Ubicación

3.1 Los puertos de repositorio deben ubicarse en `application/port/out`  
3.2 Pertenecen a la capa de aplicación  
3.3 No deben depender de infraestructura

---

#### 4. Reglas de Diseño

4.1 Definir solo las operaciones requeridas por los casos de uso  
4.2 Mantener interfaces pequeñas y enfocadas  
4.3 Usar nombres de métodos orientados al negocio  
4.4 No exponer detalles de persistencia  
4.5 No asumir ninguna base de datos específica

---

#### 5. Tipos de Datos

5.1 Los puertos de repositorio deben trabajar solo con modelos de dominio  
5.2 No deben exponer entidades JPA ni modelos de base de datos  
5.3 Los tipos de retorno deben ser amigables para el dominio (por ejemplo, Optional, List)  
5.4 Nunca deben devolver null

---

#### 6. Independencia del Framework

6.1 No usar tipos específicos del framework (por ejemplo, Spring Pageable)  
6.2 No incluir anotaciones (`@Repository`)  
6.3 No depender de JPA, Hibernate ni ningún ORM

---

#### 7. Nivel de Abstracción

7.1 El puerto de repositorio define QUÉ necesita la aplicación  
7.2 No define CÓMO se recuperan o almacenan los datos  
7.3 Los detalles de implementación pertenecen a la capa de infraestructura

---

#### 8. Operaciones Opcionales

8.1 Puede incluirse paginación si es necesario  
8.2 Pueden añadirse operaciones de filtrado o búsqueda  
8.3 Estas operaciones deben seguir orientadas al dominio

---

#### 9. Por Qué Este Paso Es Importante

9.1 Refuerza una separación limpia entre aplicación e infraestructura  
9.2 Mejora la capacidad de prueba (fácil de mockear)  
9.3 Permite cambiar la persistencia sin afectar la lógica de negocio  
9.4 Mantiene la arquitectura flexible y mantenible

---

#### 10. Errores Comunes

10.1 Usar entidades JPA en los puertos de repositorio  
10.2 Devolver tipos específicos del framework  
10.3 Añadir anotaciones o lógica de persistencia  
10.4 Definir demasiados métodos innecesarios  
10.5 Diseñar repositorios en función de la base de datos en lugar de los casos de uso

### 4.3 Implementar los Casos de Uso

Después de definir los puertos de repositorio, el siguiente paso es implementar los casos de uso.

Un caso de uso representa una acción u operación de negocio específica.  
Define cómo se comporta el sistema desde la perspectiva del cliente.

---

#### 1. Propósito

1.1 Definir el comportamiento de la aplicación  
1.2 Orquestar las interacciones entre dominio y puertos de repositorio  
1.3 Encapsular la lógica y reglas de negocio  
1.4 Actuar como punto de entrada de la capa de aplicación

---

#### 2. Responsabilidades

2.1 Ejecutar una única operación de negocio  
2.2 Coordinar llamadas a los puertos de repositorio  
2.3 Aplicar validaciones y reglas de negocio  
2.4 Devolver modelos de dominio o datos de respuesta

---

#### 3. Estructura

3.1 Cada caso de uso debe definirse como una interfaz (puerto de entrada)  
3.2 La implementación debe ubicarse en `application/usecase`  
3.3 La implementación debe depender solo de puertos, no de infraestructura

---

#### 4. Reglas de Diseño

4.1 Definir un caso de uso por acción  
4.2 Usar nombres claros y expresivos  
4.3 Mantener los casos de uso pequeños y enfocados  
4.4 Evitar combinar múltiples responsabilidades en un mismo caso de uso  
4.5 No incluir lógica de infraestructura

---

#### 5. Dependencias

5.1 Los casos de uso dependen de puertos de repositorio (puertos de salida)  
5.2 Las dependencias deben inyectarse a través del constructor  
5.3 El caso de uso nunca debe instanciar directamente sus dependencias  
5.4 El caso de uso debe depender solo de abstracciones (interfaces)  
5.5 El puerto de repositorio define el límite entre aplicación e infraestructura

---

#### 6. Entrada y Salida

6.1 Los casos de uso pueden recibir:
- valores primitivos (por ejemplo, identificadores)
- objetos command/query para entradas complejas

6.2 Los casos de uso pueden devolver:
- modelos de dominio
- resultados opcionales
- colecciones

6.3 Los casos de uso nunca deben devolver null

---

#### 7. Independencia del Framework

7.1 No incluir anotaciones de framework (`@Service`, etc.)  
7.2 No depender de Spring ni de ningún framework  
7.3 La capa de aplicación debe seguir siendo agnóstica al framework

---

#### 8. Flujo de Interacción

8.1 Recibir la entrada del llamador (por ejemplo, un controller)  
8.2 Validar la entrada si es necesario  
8.3 Llamar a los puertos de repositorio  
8.4 Aplicar lógica de negocio  
8.5 Devolver el resultado

---

#### 9. Por Qué Este Paso Es Importante

9.1 Define el comportamiento del sistema  
9.2 Mantiene la lógica de negocio fuera de los controladores  
9.3 Garantiza una separación clara de responsabilidades  
9.4 Hace que la aplicación sea fácil de probar

---

#### 10. Errores Comunes

10.1 Poner lógica de negocio en los controladores  
10.2 Acceder a persistencia directamente sin puertos  
10.3 Crear servicios grandes y genéricos con múltiples responsabilidades  
10.4 Mezclar casos de uso con lógica de infraestructura  
10.5 Devolver null en lugar de tipos adecuados para el dominio

### 4.4 Implementar la Capa de Persistencia

Después de implementar los casos de uso, el siguiente paso es implementar la capa de persistencia.

La capa de persistencia es responsable de almacenar y recuperar datos de la base de datos.  
Proporciona la implementación concreta de los puertos de repositorio definidos en la capa de aplicación.

---

#### 1. Propósito

1.1 Representar cómo se almacenan los datos en la base de datos  
1.2 Implementar el modelo de persistencia requerido por JPA  
1.3 Proporcionar los componentes técnicos necesarios para persistir y recuperar datos  
1.4 Mantener las preocupaciones de persistencia aisladas del modelo de dominio

---

#### 2. Entidad de Persistencia

2.1 Cada funcionalidad debe definir una entidad JPA que represente la estructura de la base de datos  
2.2 Las entidades JPA deben ubicarse en `infrastructure/out/persistence/entity`  
2.3 Una entidad JPA es un modelo de persistencia, no un modelo de dominio  
2.4 Puede incluir campos técnicos requeridos por la base de datos y el ORM

---

#### 3. Responsabilidades de la Entidad JPA

3.1 Mapear la tabla y columnas utilizadas en la base de datos  
3.2 Definir el identificador técnico usado por persistencia  
3.3 Incluir campos de auditoría cuando sea necesario (por ejemplo, `createdAt`, `updatedAt`)  
3.4 Definir restricciones de base de datos como longitud, nulabilidad y unicidad  
3.5 Soportar los requisitos del ciclo de vida del ORM

---

#### 4. Reglas de Diseño

4.1 Las entidades JPA pueden contener identificadores técnicos como `id`  
4.2 Las entidades JPA pueden contener campos de auditoría gestionados por la base de datos o la capa de persistencia  
4.3 Las entidades JPA deben incluir anotaciones JPA como `@Entity`, `@Table`, `@Id` y `@Column`  
4.4 Las entidades JPA no deben usarse como modelos de dominio  
4.5 Las entidades JPA deben permanecer dentro de la capa de infraestructura

---

#### 5. Igualdad

5.1 La igualdad en las entidades JPA debe basarse en la identidad de persistencia  
5.2 El identificador técnico es la referencia para la igualdad una vez que la entidad ha sido persistida  
5.3 La igualdad de una entidad JPA no debe mezclarse con las reglas de identidad del dominio

---

#### 6. Separación del Dominio

6.1 El modelo de dominio representa el concepto de negocio  
6.2 La entidad JPA representa la estructura de la base de datos  
6.3 Estos dos modelos tienen responsabilidades distintas y deben permanecer separados  
6.4 Las preocupaciones técnicas de persistencia nunca deben filtrarse a la capa de dominio

---

#### 7. Por Qué Este Paso Es Importante

7.1 Mantiene las preocupaciones de base de datos aisladas de la lógica de negocio  
7.2 Permite que el modelo de dominio siga siendo limpio e independiente del framework  
7.3 Soporta los requisitos del ORM sin contaminar la capa de aplicación  
7.4 Hace que la implementación de persistencia sea explícita y mantenible

#### 8. Repositorio Spring Data

8.1 La capa de persistencia debe definir una interfaz de repositorio usando Spring Data JPA

8.2 Este repositorio es responsable de interactuar directamente con la base de datos

8.3 Debe ubicarse en `infrastructure/out/persistence/repository`

---

#### 9. Responsabilidades del Repositorio

9.1 Ejecutar operaciones de base de datos (CRUD)  
9.2 Proporcionar métodos de consulta según necesidades de persistencia  
9.3 Trabajar exclusivamente con entidades JPA

---

#### 10. Reglas de Diseño

10.1 El repositorio debe extender `JpaRepository` (o similar)  
10.2 Debe operar solo sobre entidades JPA, nunca sobre modelos de dominio  
10.3 Puede definir métodos de consulta usando las convenciones de Spring Data  
10.4 No debe contener lógica de negocio  
10.5 No debe exponerse fuera de la capa de infraestructura

---

#### 11. Separación de la Capa de Aplicación

11.1 La capa de aplicación no debe depender directamente de este repositorio  
11.2 El repositorio no debe implementar casos de uso de negocio  
11.3 Es un componente interno de infraestructura

---

#### 12. Por Qué Este Paso Es Importante

12.1 Encapsula la lógica de acceso a base de datos  
12.2 Aprovecha Spring Data para las operaciones de persistencia  
12.3 Mantiene aislados los detalles de persistencia  
12.4 Prepara la base para el adaptador de persistencia

#### 13. Adaptador de Persistencia

13.1 La capa de persistencia debe definir un adaptador que implemente el puerto de repositorio

13.2 El adaptador debe ubicarse en `infrastructure/out/persistence/adapter`

13.3 El adaptador es el puente entre la capa de aplicación y la capa de persistencia

13.4 Proporciona la implementación concreta de las operaciones declaradas en el puerto de repositorio

---

#### 14. Responsabilidades del Adaptador

14.1 Implementar el puerto de salida definido en `application/port/out`

14.2 Delegar el acceso a base de datos al repositorio Spring Data

14.3 Convertir entre modelos de dominio y entidades JPA

14.4 Mantener los detalles de persistencia aislados de la capa de aplicación

---

#### 15. Reglas de Diseño

15.1 El adaptador debe implementar el puerto de repositorio correspondiente

15.2 El adaptador debe depender del repositorio Spring Data

15.3 El adaptador no debe exponer entidades JPA fuera de la capa de infraestructura

15.4 El adaptador debe devolver modelos de dominio, no modelos de persistencia

15.5 El adaptador no debe contener lógica de negocio

---

#### 16. Flujo de Dependencias

16.1 El caso de uso depende del puerto de repositorio

16.2 El puerto de repositorio es implementado por el adaptador de persistencia

16.3 El adaptador de persistencia depende del repositorio Spring Data

16.4 El repositorio Spring Data trabaja con entidades JPA y la base de datos

---

#### 17. Por Qué Este Paso Es Importante

17.1 Completa el contrato definido en la capa de aplicación

17.2 Mantiene los casos de uso independientes de JPA y Spring Data

17.3 Centraliza los detalles de implementación relacionados con persistencia

17.4 Preserva la separación entre dominio, aplicación e infraestructura

#### 18. Specifications (Opcional)

18.1 Algunas funcionalidades pueden requerir criterios de consulta dinámicos o componibles

18.2 En esos casos, las specifications pueden implementarse en `infrastructure/out/persistence/specification`

18.3 Las specifications forman parte de la capa de persistencia y deben trabajar con entidades JPA, no con modelos de dominio

18.4 Cada specification debe representar una condición de consulta de persistencia

18.5 Las specifications deben ser pequeñas, enfocadas y reutilizables

---

#### 19. Responsabilidades de las Specifications

19.1 Encapsular lógica de filtrado dinámico

19.2 Permitir combinar múltiples condiciones de consulta

19.3 Mantener la construcción de consultas fuera del adaptador y de la lógica de implementación del repositorio

19.4 Mejorar la legibilidad y mantenibilidad en búsquedas complejas

---

#### 20. Integración con el Repositorio

20.1 Cuando se requieran specifications, el repositorio Spring Data también debe extender `JpaSpecificationExecutor`

20.2 Esto permite ejecutar consultas dinámicas basadas en specifications

20.3 El adaptador de persistencia podrá combinar y usar estas specifications para soportar casos de uso de filtrado y búsqueda

---

#### 21. Reglas de Diseño

21.1 Las specifications son opcionales y solo deben introducirse cuando realmente sean necesarias

21.2 Deben permanecer en la capa de infraestructura

21.3 No deben contener lógica de negocio

21.4 No deben exponerse directamente a la capa de aplicación

21.5 Deben operar solo sobre atributos de persistencia y relaciones entre entidades JPA

---

#### 22. Por Qué Este Paso Es Importante

22.1 Mantiene la lógica de consulta compleja aislada de los casos de uso

22.2 Evita definiciones excesivas de métodos en los repositorios

22.3 Soporta mecanismos de filtrado flexibles y mantenibles

22.4 Preserva la separación entre lógica de aplicación y preocupaciones de persistencia

### 4.5 Implementar los Mappers

Después de implementar la capa de persistencia, el siguiente paso es implementar los mappers.

Los mappers son responsables de transformar datos entre las diferentes capas de la aplicación.

Permiten que cada capa trabaje con sus propios modelos manteniendo una separación clara de responsabilidades.

---

#### 1. Propósito

1.1 Transformar datos entre capas

1.2 Mantener separados los modelos de dominio, persistencia y API

1.3 Centralizar la lógica de mapeo en componentes dedicados

1.4 Evitar que detalles de implementación se filtren entre capas

---

#### 2. Tipos de Mappers

2.1 Persistence mappers
- Convierten entre entidades JPA y modelos de dominio
- Son utilizados por el adaptador de persistencia
- Deben ubicarse en `infrastructure/out/persistence/mapper`

2.2 Web mappers
- Convierten entre modelos de dominio y DTOs
- Son utilizados por los controladores
- Deben ubicarse en `infrastructure/in/web/mapper`

---

#### 3. Responsabilidades

3.1 Los persistence mappers deben transformar modelos de persistencia en modelos de dominio

3.2 Los persistence mappers deben transformar modelos de dominio en modelos de persistencia cuando sea necesario

3.3 Los web mappers deben transformar modelos de dominio en DTOs de respuesta

3.4 Los web mappers pueden transformar DTOs de petición en objetos command/query o estructuras de entrada relacionadas con el dominio cuando sea necesario

3.5 Los mappers deben encargarse únicamente de la transformación de datos

---

#### 4. Reglas de Diseño

4.1 Los mappers no deben contener lógica de negocio

4.2 Los mappers no deben acceder a repositorios ni a sistemas externos

4.3 Los mappers deben permanecer pequeños y enfocados

4.4 Cada mapper debe tener una responsabilidad clara y limitada

4.5 La lógica de mapeo no debe duplicarse en controladores, casos de uso o adaptadores

---

#### 5. Separación de Mappers

5.1 Los persistence mappers y los web mappers forman parte de la capa de mapeo, pero cumplen propósitos diferentes

5.2 Deben explicarse conjuntamente como parte del mismo paso

5.3 Deben implementarse en paquetes distintos según su capa

5.4 No deben mezclarse en el mismo paquete o componente

5.5 Cada mapper debe operar solo dentro de los límites de su propia capa

---

#### 6. Uso de Frameworks

6.1 Puede usarse MapStruct para web mappers o persistence mappers cuando sea apropiado

6.2 También pueden utilizarse mappers manuales cuando la transformación sea simple o se prefiera un control más explícito

6.3 El enfoque elegido debe mantener la lógica de mapeo aislada y mantenible

---

#### 7. Por Qué Este Paso Es Importante

7.1 Evita fugas de modelos entre capas

7.2 Preserva una separación clara de responsabilidades

7.3 Hace que las transformaciones sean explícitas y reutilizables

7.4 Mejora la legibilidad y mantenibilidad de la base de código

---

#### 8. Errores Comunes

8.1 Devolver entidades JPA directamente desde la API

8.2 Usar DTOs como modelos de dominio

8.3 Colocar lógica de mapeo dentro de controladores o casos de uso

8.4 Mezclar responsabilidades de mapeo de persistencia y web en el mismo componente

8.5 Añadir reglas de negocio dentro de los mappers

### 4.6 Definir los DTOs

Después de implementar los mappers, el siguiente paso es definir los DTOs.

Los DTOs (Data Transfer Objects) representan los datos expuestos por la API.  
Definen la estructura de las peticiones y respuestas intercambiadas con clientes externos.

---

#### 1. Propósito

1.1 Definir el contrato de API expuesto a los clientes

1.2 Separar los modelos de API de los modelos de dominio

1.3 Controlar qué datos se reciben y devuelven por el sistema

1.4 Evitar que los modelos internos se expongan directamente

---

#### 2. Responsabilidades

2.1 Representar payloads de petición y respuesta

2.2 Exponer solo los campos requeridos por el contrato de API

2.3 Permanecer independientes de los modelos de persistencia

2.4 Evitar filtrar detalles de implementación interna

---

#### 3. Ubicación

3.1 Los DTOs deben ubicarse en `infrastructure/in/web/dto`

3.2 Pertenecen a la capa web de entrada

3.3 No deben ubicarse en las capas de dominio ni aplicación

---

#### 4. Reglas de Diseño

4.1 Los DTOs deben ser simples portadores de datos

4.2 Los DTOs no deben contener lógica de negocio

4.3 Los DTOs no deben contener anotaciones de persistencia

4.4 Los DTOs no deben usarse como modelos de dominio

4.5 Los DTOs deben incluir solo los campos requeridos por la API

---

#### 5. DTOs de Petición y Respuesta

5.1 Los DTOs de petición representan los datos recibidos desde los clientes

5.2 Los DTOs de respuesta representan los datos devueltos a los clientes

5.3 Los DTOs de petición y respuesta pueden diferir según el caso de uso

5.4 El diseño de los DTOs debe estar guiado por el contrato de API, no por la estructura de la base de datos

---

#### 6. Estilo de Implementación

6.1 Los DTOs pueden implementarse como records de Java cuando sea apropiado

6.2 Los records son adecuados cuando el DTO es inmutable y solo transporta datos

6.3 Definiciones más simples de DTO mejoran la legibilidad y reducen boilerplate

---

#### 7. Separación de Otros Modelos

7.1 Los DTOs no son modelos de dominio

7.2 Los DTOs no son entidades JPA

7.3 Los DTOs deben permanecer aislados de preocupaciones de negocio y persistencia

7.4 La conversión entre DTOs y modelos de dominio debe manejarse mediante web mappers

---

#### 8. Por Qué Este Paso Es Importante

8.1 Define un contrato externo claro para la API

8.2 Evita la exposición de modelos internos

8.3 Mantiene la capa de API independiente de las capas de dominio y persistencia

8.4 Hace que las estructuras de petición y respuesta sean explícitas y mantenibles

---

#### 9. Errores Comunes

9.1 Usar modelos de dominio directamente en los controladores

9.2 Devolver entidades JPA en respuestas de API

9.3 Añadir lógica de negocio dentro de los DTOs

9.4 Diseñar DTOs en función de tablas de base de datos en lugar de necesidades de API

9.5 Reutilizar el mismo DTO para operaciones no relacionadas sin una intención clara

### 4.7 Configurar los Beans

Después de implementar los casos de uso, el siguiente paso es configurar los beans de la aplicación.

Este paso es responsable de conectar los componentes de la aplicación y proporcionar
las implementaciones concretas requeridas en tiempo de ejecución.

---

#### 1. Propósito

1.1 Conectar los casos de uso con sus dependencias

1.2 Proporcionar implementaciones concretas para los puertos de repositorio

1.3 Ensamblar la aplicación usando inyección de dependencias

1.4 Mantener la capa de aplicación independiente del framework

---

#### 2. Responsabilidades

2.1 Instanciar implementaciones de casos de uso

2.2 Inyectar puertos de repositorio en los casos de uso

2.3 Definir cómo se conectan los componentes

2.4 Delegar la resolución de dependencias al framework

---

#### 3. Ubicación

3.1 Las clases de configuración deben ubicarse en `infrastructure/config`

3.2 Pertenecen a la capa de infraestructura

3.3 No deben ubicarse en las capas de dominio ni aplicación

---

#### 4. Reglas de Diseño

4.1 Usar clases de configuración para definir beans de la aplicación

4.2 Usar definiciones de beans para instanciar casos de uso

4.3 Inyectar dependencias a través de parámetros de método

4.4 No instanciar dependencias manualmente dentro de los casos de uso

4.5 No incluir lógica de negocio en clases de configuración

---

#### 5. Flujo de Dependencias

5.1 Los casos de uso dependen de puertos de repositorio

5.2 Los puertos de repositorio son implementados por adaptadores de persistencia

5.3 Las clases de configuración conectan los casos de uso con sus implementaciones

5.4 El framework resuelve e inyecta dependencias en tiempo de ejecución

---

#### 6. Aislamiento del Framework

6.1 La capa de aplicación debe permanecer libre de anotaciones de framework

6.2 La configuración específica del framework debe aislarse en la capa de infraestructura

6.3 La inyección de dependencias debe gestionarse externamente a los casos de uso

---

#### 7. Por Qué Este Paso Es Importante

7.1 Mantiene la capa de aplicación independiente del framework

7.2 Centraliza la creación de objetos y su conexión

7.3 Refuerza los principios de inyección de dependencias

7.4 Mejora la mantenibilidad y la capacidad de prueba

---

#### 8. Errores Comunes

8.1 Instanciar dependencias dentro de los casos de uso

8.2 Añadir anotaciones de framework dentro de la capa de aplicación

8.3 Mezclar lógica de configuración con lógica de negocio

8.4 Saltarse los puertos y conectar directamente componentes de infraestructura

### 4.8 Exponer la API (Controllers)

Después de configurar los beans de la aplicación, el siguiente paso es exponer la API a través de controladores.

Los controladores son el punto de entrada de la capa web.  
Reciben peticiones HTTP, validan la entrada, invocan casos de uso y devuelven respuestas HTTP.

---

#### 1. Propósito

1.1 Exponer la funcionalidad de la aplicación mediante endpoints HTTP

1.2 Recibir peticiones de clientes externos

1.3 Delegar la ejecución de negocio a los casos de uso

1.4 Devolver respuestas HTTP estructuradas

---

#### 2. Responsabilidades

2.1 Definir endpoints de API

2.2 Validar la entrada recibida

2.3 Llamar al caso de uso apropiado

2.4 Transformar modelos de dominio en DTOs de respuesta mediante web mappers

2.5 Devolver la respuesta HTTP correspondiente

---

#### 3. Ubicación

3.1 Los controladores deben ubicarse en `infrastructure/in/web`

3.2 Pertenecen a la capa web de entrada

3.3 No deben ubicarse en las capas de dominio ni aplicación

---

#### 4. Reglas de Diseño

4.1 Los controladores deben mantenerse ligeros

4.2 Los controladores no deben contener lógica de negocio

4.3 Los controladores deben depender de puertos de entrada (casos de uso), no de componentes de persistencia

4.4 Los controladores deben usar DTOs para payloads de petición y respuesta

4.5 Los controladores deben usar web mappers para transformar modelos de dominio en DTOs

---

#### 5. Validación de Entrada

5.1 La validación de entrada debe realizarse en el límite de la API

5.2 Pueden usarse anotaciones de validación para variables de ruta, parámetros de consulta y cuerpos de petición

5.3 La entrada inválida debe rechazarse antes de llegar a la lógica de negocio

5.4 Las reglas de validación deben estar alineadas con el contrato de la API

---

#### 6. Reglas de Dependencia

6.1 Los controladores deben depender de casos de uso definidos en `application/port/in`

6.2 Los controladores no deben acceder directamente a puertos de repositorio

6.3 Los controladores no deben acceder directamente a repositorios JPA

6.4 Los controladores pueden depender de web mappers para la transformación de respuestas

---

#### 7. Gestión de Respuestas

7.1 Los controladores deben devolver respuestas orientadas a HTTP

7.2 Los modelos de dominio no deben devolverse directamente a clientes externos

7.3 Los DTOs de respuesta deben definir la estructura de salida de la API

7.4 Los códigos de estado HTTP deben reflejar el resultado de la operación

---

#### 8. Separación de Responsabilidades

8.1 Los controladores gestionan preocupaciones HTTP

8.2 Los casos de uso gestionan el comportamiento de la aplicación y el flujo de negocio

8.3 Los adaptadores de persistencia gestionan el acceso a datos

8.4 Los mappers gestionan la transformación de datos entre capas

---

#### 9. Por Qué Este Paso Es Importante

9.1 Expone la aplicación de manera controlada y explícita

9.2 Mantiene las preocupaciones HTTP aisladas de la lógica de negocio

9.3 Preserva el flujo de dependencias de la arquitectura

9.4 Hace que el contrato de API sea claro y mantenible

---

#### 10. Errores Comunes

10.1 Añadir lógica de negocio dentro de los controladores

10.2 Acceder directamente a repositorios desde los controladores

10.3 Devolver directamente modelos de dominio o entidades JPA en las respuestas

10.4 Realizar transformaciones de datos manualmente dentro de los controladores

10.5 Mezclar preocupaciones HTTP con preocupaciones de persistencia