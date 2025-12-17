package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Ticket;
import marcomanfrin.softwareops.enums.TicketStatus;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface ITicketService {
    Ticket createTicket(String name, String description);
    Optional<Ticket> getTicketById(UUID id);
    List<Ticket> getAllTickets();
    Ticket updateTicket(UUID id, String name, String description);
    void deleteTicket(UUID id);
    Ticket changeTicketStatus(UUID id, TicketStatus status);
    Ticket closeTicket(UUID ticketId);        // alias narrativo
    List<Ticket> getTicketsByStatus(TicketStatus status);
    List<Ticket> getTicketsByClient(UUID clientId);
    List<Ticket> getTicketsByPlant(UUID plantId);

}
