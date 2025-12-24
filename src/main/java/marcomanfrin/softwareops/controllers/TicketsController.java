package marcomanfrin.softwareops.controllers;

import jakarta.validation.Valid;
import marcomanfrin.softwareops.DTO.tickets.CreateTicketRequest;
import marcomanfrin.softwareops.DTO.tickets.TicketResponse;
import marcomanfrin.softwareops.DTO.tickets.UpdateTicketRequest;
import marcomanfrin.softwareops.entities.Ticket;
import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.enums.TicketStatus;
import marcomanfrin.softwareops.services.ITicketService;
import marcomanfrin.softwareops.services.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse createTicket(@Valid @RequestBody CreateTicketRequest request) {
        Ticket ticket = ticketService.createTicket(request);
        return toResponse(ticket);
    }

    @GetMapping
    public List<TicketResponse> getAllTickets(
            @RequestParam(name = "status", required = false) TicketStatus status,
            @RequestParam(name = "clientId", required = false) UUID clientId,
            @RequestParam(name = "plantId", required = false) UUID plantId
    ) {
        List<Ticket> tickets;

        if (status != null) {
            tickets = ticketService.getTicketsByStatus(status);
        } else if (clientId != null) {
            tickets = ticketService.getTicketsByClient(clientId);
        } else if (plantId != null) {
            tickets = ticketService.getTicketsByPlant(plantId);
        } else {
            tickets = ticketService.getAllTickets();
        }

        return tickets.stream()
                .map(this::toResponse)
                .toList();
    }


    @GetMapping("/{id}")
    public TicketResponse getTicketById(@PathVariable UUID id) {
        return toResponse(ticketService.getTicketOrThrow(id));
    }

    @PatchMapping("/{id}")
    public TicketResponse patchTicket(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTicketRequest request
    ) {
        return toResponse(ticketService.patchTicket(id, request));
    }

    @PostMapping("/{id}/close")
    public TicketResponse closeTicket(@PathVariable UUID id) {
        return toResponse(ticketService.closeTicket(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public void deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicket(id);
    }

    @PostMapping("/{id}/works")
    @ResponseStatus(HttpStatus.CREATED)
    public Work createWorkFromTicket(@PathVariable UUID id) {
        return workService.createWorkFromTicket(id);
    }

    private TicketResponse toResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getName(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                ticket.getResolvedAt(),
                ticket.getClient() != null ? ticket.getClient().getId() : null,
                ticket.getPlant() != null ? ticket.getPlant().getId() : null
        );
    }
}
