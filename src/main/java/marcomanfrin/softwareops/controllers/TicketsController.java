package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.DTO.tickets.CreateTicketRequest;
import marcomanfrin.softwareops.DTO.tickets.UpdateTicketRequest;
import marcomanfrin.softwareops.entities.Ticket;
import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.enums.TicketStatus;
import marcomanfrin.softwareops.services.ITicketService;
import marcomanfrin.softwareops.services.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketsController {

    @Autowired
    private ITicketService ticketService;

    @Autowired
    private IWorkService workService;

    @PostMapping
    public Ticket createTicket(@RequestBody CreateTicketRequest request) {
        return ticketService.createTicket(request.name(), request.description());
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable UUID id) {
        return ticketService.getTicketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable UUID id, @RequestBody UpdateTicketRequest request) {
        Ticket updated = null;
        if (request.name() != null || request.description() != null) {
            updated = ticketService.updateTicket(id, request.name(), request.description());
        }
        if (request.status() != null) {
           updated = ticketService.changeTicketStatus(id, request.status());
        }

        if(updated != null){
            return ResponseEntity.ok(updated);
        }

        // if nothing to update, just return the ticket
        return ticketService.getTicketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<Ticket> closeTicket(@PathVariable UUID id) {
        Ticket closedTicket = ticketService.closeTicket(id);
        if (closedTicket != null) {
            return ResponseEntity.ok(closedTicket);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status/{status}")
    public List<Ticket> getTicketsByStatus(@PathVariable TicketStatus status) {
        return ticketService.getTicketsByStatus(status);
    }

    @GetMapping("/client/{clientId}")
    public List<Ticket> getTicketsByClient(@PathVariable UUID clientId) {
        return ticketService.getTicketsByClient(clientId);
    }

    @GetMapping("/plant/{plantId}")
    public List<Ticket> getTicketsByPlant(@PathVariable UUID plantId) {
        return ticketService.getTicketsByPlant(plantId);
    }

    @PostMapping("/{id}/works")
    public Work createWorkFromTicket(@PathVariable UUID id) {
        return workService.createWorkFromTicket(id);
    }
}
