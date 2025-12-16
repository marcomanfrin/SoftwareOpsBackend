package marcomanfrin.softwareops.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String notes;
    private String orderNumber;
    private boolean invoiced;
    private LocalDateTime invoicedAt;

    @ManyToOne
    private Client primaryClient;

    @ManyToOne
    private Client finalClient;

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setInvoiced(boolean invoiced) {
        this.invoiced = invoiced;
    }

    public void setInvoicedAt(LocalDateTime invoicedAt) {
        this.invoicedAt = invoicedAt;
    }

    public void setPrimaryClient(Client primaryClient) {
        this.primaryClient = primaryClient;
    }

    public void setFinalClient(Client finalClient) {
        this.finalClient = finalClient;
    }
}