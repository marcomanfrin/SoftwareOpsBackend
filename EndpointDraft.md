# API Design – Work / Ticket / Plant Management System

## 1) AuthController (JWT)

- **POST** `/auth/register`  
  Creazione utente base + ruolo iniziale

- **POST** `/auth/login`  
  Login, ritorna JWT

- **GET** `/auth/me`  
  Profilo corrente dal token

> Nota: Logout in JWT tipicamente è client-side oppure via blacklist / refresh token.

---

## 2) UsersController (admin + technician)

Gestione utenti e immagine profilo.

- **GET** `/users`  
  Lista utenti (solo admin)

- **GET** `/users/{id}`  
  Dettaglio utente (admin o self)

- **PATCH** `/users/{id}`  
  Update profilo (nome, email, ecc.)

- **PATCH** `/users/{id}/role`  
  Cambio ruolo admin ↔ technician (solo admin)

- **POST** `/users/{id}/profile-image`  
  Upload immagine profilo (multipart/form-data)

---

## 3) ClientsController

- **POST** `/clients`
- **GET** `/clients`
- **GET** `/clients/{id}`
- **PATCH** `/clients/{id}`
- **DELETE** `/clients/{id}`

---

## 4) PlantsController

- **POST** `/plants`
- **GET** `/plants`
- **GET** `/plants/{id}`
- **PATCH** `/plants/{id}`
- **DELETE** `/plants/{id}`

### Crea lavoro da impianto

- **POST** `/plants/{id}/works`  
  Genera `WorkFromPlant`

---

## 5) TicketsController

- **POST** `/tickets`
- **GET** `/tickets`
- **GET** `/tickets/{id}`
- **PATCH** `/tickets/{id}` (incluso cambio stato)

- **DELETE** `/tickets/{id}`

### Crea lavoro da ticket

- **POST** `/tickets/{id}/works`  
  Genera `WorkFromTicket`

---

## 6) WorksController (core domain)

Operazioni sulla **commessa/lavoro**, indipendentemente dalla sorgente.

### CRUD & listing

- **GET** `/works`  
  Filtri:
  - `status`
  - `technicianId`
  - `clientId`
  - `sourceType`
  - `from`
  - `to`

- **GET** `/works/{id}`
- **PATCH** `/works/{id}` (note, scadenze, ecc.)

- **DELETE** `/works/{id}` (se consentito)

### Assegnazioni tecnici

- **POST** `/works/{id}/assignments`  
  ```json
  {
    "technicianId": "...",
    "role": "LEAD | MEMBER"
  }

- **DELETE** `/works/{id}/assignments/{assignmentId}` -> Rimuove una assegnazione esistente

- **POST** `/works/{id}/self-assign` -> Auto-assegnazione del tecnico _(endpoint opzionale, utile per UX semplificata)_

---

## Stato avanzato del lavoro

- **POST** `/works/{id}/complete` -> Segna il lavoro come completato
  Validazioni tipiche:
  - tutte le task completate
  - report presente e finalizzato (se `WorkFromTicket`)

- **POST** `/works/{id}/invoice` -> Segna il lavoro come fatturato _(solo amministrativi)_

---

## 7) TasksController (task per work)

- **POST** `/works/{workId}/tasks`
- **GET** `/works/{workId}/tasks`
- **GET** `/tasks/{taskId}`
- **PATCH** `/tasks/{taskId}` -> Aggiornamento task (titolo, note, ecc.)

- **PATCH** `/tasks/{taskId}/status`  
  Stato task:
  - `todo`
  - `in_progress`
  - `done`  
  oppure  
  - `completed: boolean`

- **DELETE** `/tasks/{taskId}`

---

## 8) ReportsController (soprattutto per WorkFromTicket)

- **POST** `/works/{workId}/reports` -> Crea report “contenitore” (se necessario)
- **GET** `/works/{workId}/report` -> Recupera il report corrente

### Righe del report

- **POST** `/works/{workId}/report/rows` -> Aggiunge una riga di report (attività svolta + tempo impiegato)
- **PATCH** `/works/{workId}/report/rows/{rowId}`
- **DELETE** `/works/{workId}/report/rows/{rowId}`

### Finalizzazione

- **POST** `/works/{workId}/report/finalize`  
  Finalizza il report:
  - calcolo ore totali
  - lock del contenuto

---

## 9) AttachmentsController

Gestione allegati con target generico.

**AttachmentTargetType**
- `WORK`
- `PLANT`
- `TICKET`
- `REPORT`

### Upload

- **POST** `/attachments` _(multipart/form-data)_

Campi esempio:
- `file`
- `targetType`
- `targetId`
- `type`

### Accesso

- **GET** `/attachments/{id}` ->Restituisce metadata + URL

- **GET** `/{targetType}/{targetId}/attachments`

Esempi:
- `/works/{id}/attachments`
- `/plants/{id}/attachments`
- `/tickets/{id}/attachments`
- `/reports/{id}/attachments`

- **DELETE** `/attachments/{id}`

> Storage consigliato: **S3**, **Cloudinary** o servizi equivalenti.

---

## 10) Extra (UX & Dashboard)

Endpoint non indispensabili, ma molto probabili.

- **GET** `/technicians`  
  Lista rapida dei tecnici per assegnazioni

- **GET** `/works/{id}/timeline`  
  Audit log del lavoro:
  - creato
  - assegnato
  - task completato
  - report finalizzato
  - completato
  - fatturato

- **GET** `/dashboard/summary`  
  KPI principali:
  - lavori aperti
  - lavori in scadenza
  - lavori completati
  - lavori da fatturare