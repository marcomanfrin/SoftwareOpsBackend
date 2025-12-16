package marcomanfrin.softwareops.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "work_reports")
public class WorkReport {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Work work;

    @ManyToOne
    private Ticket ticket;

    private BigDecimal totalHours;
    private boolean invoiced;
    private LocalDateTime invoicedAt;
    private LocalDateTime createdAt;
}