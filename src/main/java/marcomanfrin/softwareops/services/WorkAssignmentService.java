package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.User;
import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.entities.WorkAssignment;
import marcomanfrin.softwareops.enums.AssignmentRole;
import marcomanfrin.softwareops.repositories.UserRepository;
import marcomanfrin.softwareops.repositories.WorkAssignmentRepository;
import marcomanfrin.softwareops.repositories.WorkRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WorkAssignmentService implements IWorkAssignmentService {

    private final WorkAssignmentRepository workAssignmentRepository;
    private final WorkRepository workRepository;
    private final UserRepository userRepository;

    public WorkAssignmentService(WorkAssignmentRepository workAssignmentRepository, WorkRepository workRepository, UserRepository userRepository) {
        this.workAssignmentRepository = workAssignmentRepository;
        this.workRepository = workRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void assignTechnicianToWork(UUID workId, UUID technicianId, AssignmentRole role) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));
        User user = userRepository.findById(technicianId)
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        // TODO: check if the user is a technician

        WorkAssignment assignment = new WorkAssignment();
        assignment.setWork(work);
        assignment.setUser(user);
        assignment.setAssignedAt(LocalDateTime.now());
        assignment.setAssignmentRole(role);

        workAssignmentRepository.save(assignment);
    }
}
