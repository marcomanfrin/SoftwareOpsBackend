package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "works")
public abstract class Work {

    @Id
    private UUID id;

    private int progressPercent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
