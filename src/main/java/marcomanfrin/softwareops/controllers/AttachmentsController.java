package marcomanfrin.softwareops.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attachments")
public class AttachmentsController {
/* **AttachmentTargetT
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

            - **DELETE** `/attachments/{id}`*/
}
