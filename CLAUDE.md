# tecnologie da utilizzare:
1️⃣ Fondamenti di Backend & Spring

Cos’è Spring e perché si usa

Spring è un framework Java enterprise che fornisce una struttura solida per costruire applicazioni backend modulari, testabili e scalabili.
Nasce per superare i limiti dei vecchi approcci Java EE (troppo boilerplate, troppo coupling).

I concetti chiave:
•	Framework ≠ libreria → Spring controlla il flusso dell’applicazione
•	Modularità → usi solo ciò che ti serve
•	Best practices by default

Spring Boot

Spring Boot semplifica drasticamente Spring grazie a:
•	Convention over configuration
•	Auto-configurazione
•	Starter dependencies
•	Server embedded (Tomcat)

Risultato: parti subito a scrivere business logic, non configurazioni.

⸻

2️⃣ IoC & Dependency Injection (DI)

Problema

Creare manualmente le dipendenze porta a:
•	Tight coupling
•	Codice difficile da testare
•	Scarsa flessibilità

Soluzione: IoC + DI
•	Inversion of Control → non sei tu a creare gli oggetti
•	Dependency Injection → Spring li crea e li inietta

Benefici:
•	Loose coupling
•	Facilità di test (mock)
•	Manutenibilità
•	Scalabilità

Bean

Un Bean è un oggetto gestito dal container Spring.

Modi per definirli:
•	@Component, @Service, @Repository
•	@Configuration + @Bean

Concetti importanti:
•	Scope (singleton di default)
•	@Primary e @Qualifier

⸻

3️⃣ Spring Web & REST API

REST

Architettura basata su:
•	Risorse (/users, /works)
•	Metodi HTTP (GET, POST, PUT, DELETE)
•	JSON come formato di scambio

Controller

Con Spring Web:
•	@RestController
•	@RequestMapping
•	@GetMapping, @PostMapping, ecc.

DTO

Separano:
•	Payload API
•	Modello di dominio

Servono per:
•	Sicurezza
•	Validazione
•	Evoluzione API

⸻

 Pagination in Spring Data JPA (cuore della lezione)

Spring Data JPA la supporta nativamente.

Interfacce chiave spiegate:
•	Pageable
•	Page<T>

Repository

A lezione viene sottolineato che non devi scrivere query custom:
Page<Work> findAll(Pageable pageable);
È già tutto pronto.

⸻

 Pageable

Pageable rappresenta:
•	quale pagina vuoi
•	quanti elementi
•	come ordinare

Viene creato automaticamente da Spring se lo metti come parametro:
@GetMapping
public Page<Work> getWorks(Pageable pageable)
Oppure manualmente:
Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());


4️⃣ Data Access & JPA

JPA / Hibernate

Spring Data JPA:
•	Riduce boilerplate
•	Repository pronti (JpaRepository)
•	Query automatiche

Entity
•	Mappano il dominio sul DB
•	Annotazioni: @Entity, @Id, @ManyToOne, ecc.

⸻

5️⃣ Ereditarietà in JPA

JPA supporta ereditarietà tra entity, ma va mappata sul DB.

Strategie viste
1.	SINGLE_TABLE
•	Una tabella
•	Discriminator column
•	🔥 Veloce, ma molte colonne NULL
2.	JOINED
•	Tabelle separate collegate
•	Schema normalizzato
•	🔁 Più join → più costo
3.	TABLE_PER_CLASS
•	Una tabella per sottoclasse
•	❌ Duplicazione dati

Polymorphic Queries
•	JPQL è polimorfico
•	Query su classe base → ritorna anche le sottoclassi
•	Traduzione SQL dipende dalla strategia scelta

⸻

6️⃣ Validazione & Gestione Errori

Validazione

Con Bean Validation:
•	@NotBlank, @Size, @Email, ecc.
•	Validazione automatica su DTO

Global Exception Handler
•	@ControllerAdvice
•	Gestione centralizzata degli errori
•	Errori coerenti e leggibili (ErrorDTO)

⸻

7️⃣ Sicurezza: Authentication & Authorization

Concetti base
•	Authentication → chi sei
•	Authorization → cosa puoi fare

Stateful vs Stateless
•	Sessioni → stateful
•	JWT → stateless (scalabile, microservizi)

⸻

8️⃣ JWT (JSON Web Token)

Struttura
•	Header
•	Payload (claims)
•	Signature

Flow
1.	Login → verifica credenziali
2.	Generazione JWT
3.	Client salva token
4.	Token inviato in Authorization: Bearer ...
5.	Backend verifica token ad ogni richiesta

Best practice
•	Token firmato
•	Expiration breve
•	Mai dati sensibili nel payload

Spring Security
•	SecurityFilterChain
•	Disabilitare:
•	formLogin
•	httpBasic
•	sessioni
•	CSRF (per REST)
•	Custom JWT Filter
•	Estende OncePerRequestFilter
•	Valida token
•	Decide se far passare la richiesta

⸻

9️⃣ Variabili d’ambiente

Separare:
•	Configurazione
•	Codice

Usate per:
•	DB credentials
•	API keys
•	Secret JWT
•	Cloud credentials

In Spring:
•	application.properties
•	${ENV_VAR}

⚠️ Mai committarle

⸻

🔟 Third Party APIs

Principio: Delegation

Il backend non reinventa la ruota.

Esempi visti
•	Cloudinary → upload immagini
•	Mailgun → email transazionali

Benefici:
•	Scalabilità
•	Sicurezza
•	Meno responsabilità sul backend

⸻

1️⃣1️⃣ File upload (Cloudinary)
•	Endpoint multipart/form-data
•	Backend riceve file
•	Invia a Cloudinary
•	Riceve URL
•	Salva URL nel DB

⸻

1️⃣2️⃣ Email (Mailgun)
•	Invio via API HTTP
•	No SMTP server
•	Tracking e affidabilità
•	Integrazione via environment variables

⸻

1️⃣3️⃣ GraphQL

Perché GraphQL

Risolve:
•	Overfetching
•	Underfetching

Caratteristiche
•	Single endpoint
•	Client decide cosa ricevere
•	API fortemente tipizzata
•	Evoluzione senza versioning

Concetti chiave
•	Schema (SDL)
•	Types
•	Query
•	Mutation
•	Resolver

In Spring Boot
•	spring-boot-starter-graphql
•	Schema .graphqls
•	Resolver con:
•	@QueryMapping
•	@MutationMapping
•	@SchemaMapping

REST e GraphQL possono convivere

# Requisiti del progetto

You are required to build a complete backend application using Spring and PostgreSQL, demonstrating your ability to design and implement robust server-side features, including request handling, data persistence, validation, authentication, business logic structuring, and seamless interaction with the underlying database and external services when needed. The project should be fully functional and well-structured, showcasing good coding practices

**This practical project accounts for 50% of the final grade; the remaining 50% will be assessed through an oral examination**

## General Requirements

- **Project Theme:** You are free to choose the theme of your application (e.g., an e-commerce store, a task manager, a social media dashboard, a movie database, etc.)
- **Entities:** The application must include a domain model with at least eight tables, designed through coherent and meaningful relationships, and containing at least one inheritance structure that justifies a hierarchy within the domain
- **User Requirements:** The application must include a complete user management system. Each user must have an email, a password, and a profile image that can be updated after registration. In addition to these core attributes, users must include all common personal details required by the applicationʼs domain, such as name, surname, registration date, or any additional information that contributes to a realistic and fully functional profile
- **REST APIs:** The system must expose REST APIs that follow consistent principles for handling requests, responses, and errors, ensuring predictable and reliable interactions
- **Auth:** The application must implement authentication and authorization based on JWT. The user model must include at least three distinct roles, each with its own permissions and access rules
- **Queries:** Queries must be implemented to retrieve and manipulate data efficiently. These should include filtering, sorting, aggregations, and queries combining multiple conditions. JPA query methods, JPQL, or native SQL may be used. Queries should support real use cases within the application
- **Error Handling:** The project must validate all incoming data and handle errors through structured and meaningful responses. The overall application should behave reliably and present consistent patterns for both expected and unexpected situations
- **3rd Party APIs:** The backend must interact with at least two third-party APIs. The retrieved information must be incorporated meaningfully into the system and contribute to the applicationʼs internal logic or exposed functionality

## **Supporting Material**

- The project must be hosted on GitHub, including everything needed to run the application and a clear README.md explaining: project overview, running instructions, environment variables needed, features, etc.
- Students must also include a Postman collection in JSON format, containing all the requests needed to test every implemented feature. Each request must include example payloads, parameters, headers, and every detail required for immediate use

  **Any functionality not represented in the Postman collection will not be evaluated during grading**


## Attention!

❌ All general requirements are mandatory. Failure to meet these requirements will result in penalties in the final evaluation

❌ Penalties may apply if security principles or best practices illustrated during the course are not followed

✅ Optional features can be implemented to enhance the project to gain extra points. These may include integration with additional third-party APIs not covered during the course, a dedicated section of the application accessible through GraphQL, the creation of particularly complex or optimized queries or other extensions that add meaningful functionality to the system