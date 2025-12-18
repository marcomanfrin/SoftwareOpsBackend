package marcomanfrin.softwareops.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    // - **POST** `/works/{workId}/tasks`
    //- **GET** `/works/{workId}/tasks`
    //- **GET** `/tasks/{taskId}`
    //- **PATCH** `/tasks/{taskId}` -> Aggiornamento task (titolo, note, ecc.)
    //
    //- **PATCH** `/tasks/{taskId}/status`
    //  Stato task:
    //  - `todo`
    //  - `in_progress`
    //  - `done`
    //  oppure
    //  - `completed: boolean`
    //
    //- **DELETE** `/tasks/{taskId}`
}
