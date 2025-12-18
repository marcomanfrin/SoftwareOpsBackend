✦ Sì, per testare correttamente gli endpoint, è consigliabile seguire un ordine logico che rispetti le dipendenze tra le risorse. Ad esempio, non puoi richiedere i dettagli di un utente prima di averlo creato.

Ecco un ordine suggerito per il testing:

1. Creazione (POST): Inizia creando le risorse di base.
    * Crea un Client (POST /clients).
    * Crea un User (es. un tecnico) (POST /users).
    * Crea un Plant associato al cliente creato (POST /plants).
    * Crea un Ticket (POST /tickets).

2. Lettura (GET): Verifica che le risorse create esistano.
    * Leggi la lista di tutte le risorse (GET /clients, GET /users, ecc.).
    * Leggi i dettagli di una risorsa specifica usando il suo ID (GET /clients/{id}, GET /users/{id}, ecc.).

3. Generazione di Lavori (Work):
    * Crea un Work da un Plant (POST /plants/{plantId}/works).
    * Crea un Work da un Ticket (POST /tickets/{ticketId}/works).

4. Operazioni su `Work`: Ora che hai dei Work, puoi testare le operazioni correlate.
    * Assegna un tecnico a un lavoro (POST /works/{workId}/assignments).
    * Aggiungi Task a un lavoro (POST /works/{workId}/tasks).

5. Aggiornamento (PATCH/PUT): Modifica le risorse esistenti.
    * Aggiorna i dettagli di un User, Client, Task, ecc.
    * Cambia lo stato di un Ticket, Work o Task.

6. Operazioni su `Report`:
    * Crea il contenitore del report per un Work (POST /works/{workId}/reports).
    * Aggiungi righe al report (POST /works/{workId}/report/rows).
    * Finalizza il report (POST /works/{workId}/report/finalize).

7. Cancellazione (DELETE): Per ultime, testa le operazioni di cancellazione.
    * Cancella una Task, un AttachmentLink, ecc.
    * Attenzione: Cancellare una risorsa "padre" (es. un Client) potrebbe causare la cancellazione a cascata di risorse dipendenti o lasciare dati orfani, a seconda dell'implementazione del backend. Testa
      queste operazioni con cautela.

Consiglio: Esegui le richieste POST per prime e salva gli ID delle risorse create (utenti, clienti, ecc.). Potrai poi usare questi ID nelle richieste successive (GET /{id}, PATCH, DELETE) per testare l'intero
ciclo di vita di una risorsa.
