package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
import marcomanfrin.softwareops.enums.TaskStatus;

import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    private String text;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    private Work work;

    public void setText(String text) {
        this.text = text;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setWork(Work work) {
        this.work = work;
    }
}