package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Ticket;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface ITicketService {
    Ticket createTicket(String name, String description);
    Optional<Ticket> getTicketById(UUID id);
    List<Ticket> getAllTickets();
    Ticket updateTicket(UUID id, String name, String description);
    void deleteTicket(UUID id);
    Ticket changeTicketStatus(UUID id, String status);

    // to implement:
    // closeTicket(UUID ticketId) / resolveTicket(UUID ticketId) → alias narrativo che setta DONE + eventuali side-effects.
    // getTicketsByStatus(TicketStatus status)
    // getTicketsByClient(UUID clientId) / getTicketsByPlant(UUID plantId)
}
