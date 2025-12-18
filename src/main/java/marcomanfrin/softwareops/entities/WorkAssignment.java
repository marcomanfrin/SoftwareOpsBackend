package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
import marcomanfrin.softwareops.enums.AssignmentRole;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "work_assignments")
public class WorkAssignment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Work work;

    @ManyToOne
    private User user;

    private LocalDateTime assignedAt;

    @Enumerated(EnumType.STRING)
    private AssignmentRole assignmentRole;

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public void setAssignmentRole(AssignmentRole assignmentRole) {
        this.assignmentRole = assignmentRole;
    }
}