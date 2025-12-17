package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Ticket;
import marcomanfrin.softwareops.enums.TicketStatus;
import marcomanfrin.softwareops.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket createTicket(String name, String description) {
        String n = validateName(name);

        Ticket ticket = new Ticket();
        ticket.setName(n);
        ticket.setDescription(description);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> getTicketById(UUID id) {
        return ticketRepository.findById(id);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket updateTicket(UUID id, String name, String description) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found: " + id));

        ticket.setName(validateName(name));
        ticket.setDescription(description);
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(UUID id) {
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Ticket not found: " + id);
        }
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket changeTicketStatus(UUID id, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found: " + id));

        if (ticket.getStatus() == status) {
            return ticket;
        }
        ticket.setStatus(status);

        // se passa a DONE, set resolvedAt
         if (status == TicketStatus.CLOSED && ticket.getResolvedAt() == null) {
             ticket.setResolvedAt(LocalDateTime.now());
         }

        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket closeTicket(UUID ticketId) {
        return changeTicketStatus(ticketId, TicketStatus.CLOSED);
    }

    @Override
    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        if (status == null) throw new IllegalArgumentException("Status cannot be null");
        return ticketRepository.findByStatus(status);
    }

    @Override
    public List<Ticket> getTicketsByClient(UUID clientId) {
        return ticketRepository.findByClient_Id(clientId);
    }

    @Override
    public List<Ticket> getTicketsByPlant(UUID plantId) {
        return ticketRepository.findByPlant_Id(plantId);
    }

    private String validateName(String name) {
        if (name == null || name.trim().isBlank()) {
            throw new IllegalArgumentException("Ticket name cannot be blank");
        }
        return name.trim();
    }
}
