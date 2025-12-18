package marcomanfrin.softwareops.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    // - **POST** `/works/{workId}/reports` -> Crea report “contenitore” (se necessario)
    //- **GET** `/works/{workId}/report` -> Recupera il report corrente
    //
    //### Righe del report
    //
    //- **POST** `/works/{workId}/report/rows` -> Aggiunge una riga di report (attività svolta + tempo impiegato)
    //- **PATCH** `/works/{workId}/report/rows/{rowId}`
    //- **DELETE** `/works/{workId}/report/rows/{rowId}`
    //
    //### Finalizzazione
    //
    //- **POST** `/works/{workId}/report/finalize`
    //  Finalizza il report:
    //  - calcolo ore totali
    //  - lock del contenuto
}
