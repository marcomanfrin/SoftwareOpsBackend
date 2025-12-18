Application Logic & Operational Narrative

Overview

This application is a backend system designed to manage technical works and interventions for industrial plants.
It supports the full lifecycle of an intervention, from the creation of a work item starting from a plant or a support ticket, through execution by technicians, up to administrative invoicing.

The system enforces clear separation of responsibilities, strict authorization rules, and traceable workflows, ensuring consistency, scalability, and auditability.

⸻

1. User Management Model

User access and behavior are governed by two independent dimensions:

1.1 User Type (UserType)

The user type defines what kind of activities a user is allowed to perform within the organization.

TECHNICIAN
•	Operational user
•	Can be assigned to works
•	Can execute tasks
•	Can register working time and notes
•	Can compile technical reports
•	Can upload attachments related to assigned work

ADMINISTRATIVE
•	Non-operational user
•	Cannot execute tasks or register work time
•	Cannot modify technical data
•	Can:
•	View completed works
•	Mark works as invoiced
•	Manage invoicing-related metadata

⸻

1.2 User Role (UserRole)

The user role defines the level of authority within the system.
•	OWNER
•	Full system access
•	All administrative and operational privileges
•	ADMIN
•	Can create and manage users
•	Can create, assign, and manage works
•	Inherits all USER privileges
•	USER
•	Can view all system data
•	Can modify only the works and tasks assigned to them (if TECHNICIAN)

Important:
A role alone does not enable operational actions.
For example, a USER with ADMINISTRATIVE type cannot perform technician operations.

⸻

2. Core Domain Entities
   •	Client: Customer entity
   •	Plant: Industrial plant installed at a client site
   •	Ticket: Support or issue request related to a plant
   •	Work: A technical intervention or job
   •	Task: Atomic unit of work within a Work
   •	WorkLog: Time and activity registration
   •	Report: Technical report for a completed work
   •	Attachment: External file linked to domain entities

⸻

3. High-Level Operational Workflow

The logical flow of the application follows these steps:
1.	A Work is created starting from a Plant or a Ticket
2.	A Technician is assigned to the Work
3.	The Work contains a set of Tasks
4.	Tasks are executed and completed
5.	The Work Report is compiled
6.	The Work is marked as Completed
7.	The Administrative department Invoices the Work

⸻

4. Ticket Lifecycle

Tickets represent support requests or problem reports.

States
•	OPEN
•	IN_PROGRESS
•	RESOLVED
•	CLOSED

Rules
•	A ticket is created in OPEN state
•	Creating a Work from a ticket moves it to IN_PROGRESS
•	A ticket is resolved only when related works are completed
•	Closing a ticket is an administrative action

⸻

5. Work Lifecycle

A Work represents a concrete technical intervention.

Creation
•	A Work can be created:
•	From a Plant (maintenance or planned activity)
•	From a Ticket (support intervention)
•	Newly created works start in DRAFT state

Assignment
•	A Work must be assigned to a Technician
•	Assignment can be done by:
•	ADMIN or OWNER
•	Technician self-assignment (if enabled)
•	Once assigned, the Work moves to SCHEDULED

States
•	DRAFT
•	SCHEDULED
•	IN_PROGRESS
•	COMPLETED
•	CANCELLED

State Transitions
•	IN_PROGRESS is entered when:
•	The first task is started
•	Or the first work log is created
•	A Work can be marked COMPLETED only if:
•	All tasks are completed
•	A report is present (mandatory for ticket-based works)

⸻

6. Task Execution

Tasks represent atomic units of work inside a Work.

Task States
•	TODO
•	DOING
•	DONE

Rules
•	Tasks belong to exactly one Work
•	Only the assigned technician can modify task states
•	All tasks must be DONE before the Work can be completed

⸻

7. Time Tracking (Work Logs)

Technicians can register work activity through WorkLogs.

Characteristics
•	Linked to:
•	Work
•	Technician
•	Date
•	Contains:
•	Duration
•	Description
•	Optional activity type

Rules
•	Only TECHNICIAN users can create work logs
•	Work logs are read-only after the Work is completed

⸻

8. Technical Report

The Report represents the formal technical outcome of a Work.

Characteristics
•	Mandatory for works created from tickets
•	Composed of multiple rows:
•	Activity description
•	Time spent
•	Compiled by the assigned technician

Rules
•	A Work cannot be completed without a report when required
•	Reports become immutable after Work completion

⸻

9. Invoicing Process

Invoicing is handled exclusively by the Administrative department.

Rules
•	Only ADMINISTRATIVE users (or ADMIN/OWNER) can invoice a work
•	Only completed works can be invoiced
•	Invoicing consists of:
•	Marking the work as invoiced
•	Storing invoicing metadata (date, reference)

Invoicing does not alter the technical state of a work.

⸻

10. Attachments

Attachments are external files linked to domain entities.

Characteristics
•	Can be linked to:
•	Plant
•	Ticket
•	Work
•	Task
•	Stored externally (cloud storage)
•	Only metadata is stored in the database

Rules
•	Technicians can upload attachments for assigned works
•	Administrative users can only view attachments

⸻

11. Data Integrity & Audit Rules
    •	All operational entities track:
    •	createdAt, createdBy
    •	updatedAt
    •	Completed works are immutable
    •	Only ADMIN or OWNER can reopen or cancel a work
    •	All state transitions are controlled at service level

⸻

12. Architectural Principles
    •	Controllers handle HTTP requests only
    •	Business rules are enforced in services
    •	Authorization is centralized and declarative
    •	The domain model remains consistent and explicit
    •	No business logic is implemented at controller level