package marcomanfrin.softwareops.DTO;

import marcomanfrin.softwareops.enums.WorkStatus;

public record UpdateWorkRequest(WorkStatus status) {
}
