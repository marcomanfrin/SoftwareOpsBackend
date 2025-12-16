package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.WorkAssignment;
import marcomanfrin.softwareops.enums.AssignmentRole;

import java.util.UUID;

public interface IWorkAssignmentService {
    WorkAssignment assignTechnicianToWork(UUID workId, UUID technicianId, AssignmentRole role);
}
