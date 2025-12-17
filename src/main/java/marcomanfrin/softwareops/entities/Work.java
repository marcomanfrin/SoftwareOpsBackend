package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
import marcomanfrin.softwareops.enums.WorkStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "works")
public abstract class Work {

    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private WorkStatus status;

    public WorkStatus getStatus() {
        return status;
    }

    private int progressPercent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getId() {
        return id;
    }

    public void setStatus(WorkStatus status) {
        this.status = status;
    }

    public void setProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
