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
}