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
        Ticket ticket = new Ticket();
        ticket.setName(name);
        ticket.setDescription(description);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
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
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setName(name);
        ticket.setDescription(description);
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(UUID id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket changeTicketStatus(UUID id, String status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setStatus(TicketStatus.valueOf(status.toUpperCase()));
        return ticketRepository.save(ticket);
    }
}
