package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Task;
import marcomanfrin.softwareops.enums.TaskStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITaskService {
    Task createTask(UUID workId, String text);
    Optional<Task> getTaskById(UUID id);
    List<Task> getTasksByWorkId(UUID workId);
    Task updateTask(UUID id, String text);
    void deleteTask(UUID id);
    Task updateTaskStatus(UUID id, TaskStatus status);
    Task completeTask(UUID taskId);
    List<Task> completeAllTasks(UUID workId);
    boolean areAllTasksCompleted(UUID workId);
    double getCompletionRate(UUID workId);
    List<Task> getTasksByStatus(UUID workId, TaskStatus status);
}
