package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
import marcomanfrin.softwareops.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime UpdatedAt;
    private LocalDateTime ResolvedAt;
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne(optional = true) // o false se obbligatorio
    @JoinColumn(name = "plant_id")
    private Plant plant;

    public LocalDateTime getResolvedAt() {
        return ResolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        ResolvedAt = resolvedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        UpdatedAt = updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}