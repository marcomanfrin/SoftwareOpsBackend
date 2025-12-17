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

    public UUID getId() {
        return id;
    }

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
    private boolean finalized;

    public LocalDateTime getFinalizedAt() {
        return finalizedAt;
    }

    public void setFinalizedAt(LocalDateTime finalizedAt) {
        this.finalizedAt = finalizedAt;
    }

    private LocalDateTime finalizedAt;

    public boolean isFinalized() {
        return finalized;
    }

    public boolean isInvoiced() {
        return invoiced;
    }


    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setTotalHours(BigDecimal totalHours) {
        this.totalHours = totalHours;
    }

    public void setInvoiced(boolean invoiced) {
        this.invoiced = invoiced;
    }

    public void setInvoicedAt(LocalDateTime invoicedAt) {
        this.invoicedAt = invoicedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}