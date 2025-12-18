package marcomanfrin.softwareops.DTO;

import marcomanfrin.softwareops.enums.AssignmentRole;
import java.util.UUID;

public record AssignTechnicianRequest (UUID technicianId, AssignmentRole role){}
