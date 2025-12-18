package marcomanfrin.softwareops.DTO;

import marcomanfrin.softwareops.enums.TaskStatus;

public record UpdateTaskStatusRequest(TaskStatus status) {
}
