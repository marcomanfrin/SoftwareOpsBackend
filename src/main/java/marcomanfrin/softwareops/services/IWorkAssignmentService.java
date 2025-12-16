package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.enums.AssignmentRole;

import java.util.UUID;

public interface IWorkAssignmentService {
    void assignTechnicianToWork(UUID workId, UUID technicianId, AssignmentRole role);

    // to implement:
    // getAssignmentsByWorkId(UUID workId) → per vedere chi è assegnato (admin e tecnico).
    // getAssignmentsByTechnicianId(UUID technicianId) → “i miei incarichi”.
    //

}
