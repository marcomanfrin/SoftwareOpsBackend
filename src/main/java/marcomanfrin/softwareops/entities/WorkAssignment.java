package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
import marcomanfrin.softwareops.enums.AssignmentRole;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "work_assignments")
public class WorkAssignment {

    @Id
    private UUID id;

    @ManyToOne
    private Work work;

    @ManyToOne
    private User user;

    private LocalDateTime assignedAt;

    @Enumerated(EnumType.STRING)
    private AssignmentRole assignmentRole;
}