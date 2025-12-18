package marcomanfrin.softwareops.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/works")
public class WorksController {
    // Operazioni sulla **commessa/lavoro**, indipendentemente dalla sorgente.

    // ### CRUD & listing
    //
    //- **GET** `/works`
    //  Filtri:
    //  - `status`
    //  - `technicianId`
    //  - `clientId`
    //  - `sourceType`
    //  - `from`
    //  - `to`
    //
    //- **GET** `/works/{id}`
    //- **PATCH** `/works/{id}` (note, scadenze, ecc.)
    //
    //- **DELETE** `/works/{id}` (se consentito)
    //
    //### Assegnazioni tecnici
    //
    //- **POST** `/works/{id}/assignments`
    //  ```json
    //  {
    //    "technicianId": "...",
    //    "role": "LEAD | MEMBER"
    //  }
    //
    //- **DELETE** `/works/{id}/assignments/{assignmentId}` -> Rimuove una assegnazione esistente
    //
    //- **POST** `/works/{id}/self-assign` -> Auto-assegnazione del tecnico _(endpoint opzionale, utile per UX semplificata)_
    //
    //---
    //
    //## Stato avanzato del lavoro
    //
    //- **POST** `/works/{id}/complete` -> Segna il lavoro come completato
    //  Validazioni tipiche:
    //  - tutte le task completate
    //  - report presente e finalizzato (se `WorkFromTicket`)
    //
    //- **POST** `/works/{id}/invoice` -> Segna il lavoro come fatturato _(solo amministrativi)_
    //
    //---
}
