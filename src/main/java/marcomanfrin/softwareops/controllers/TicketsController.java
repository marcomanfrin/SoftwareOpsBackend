package marcomanfrin.softwareops.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketsController {
    // **POST** `/tickets`
    // **GET** `/tickets`
    // **GET** `/tickets/{id}`
    // **PATCH** `/tickets/{id}` (incluso cambio stato)
    // **DELETE** `/tickets/{id}`

    // **POST** `/tickets/{id}/works` -> Genera `WorkFromTicket`
}
