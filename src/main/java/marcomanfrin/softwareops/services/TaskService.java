package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Task;
import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.enums.TaskStatus;
import marcomanfrin.softwareops.repositories.TaskRepository;
import marcomanfrin.softwareops.repositories.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final WorkRepository workRepository;

    public TaskService(TaskRepository taskRepository, WorkRepository workRepository) {
        this.taskRepository = taskRepository;
        this.workRepository = workRepository;
    }

    @Override
    public Task createTask(UUID workId, String text) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));
        Task task = new Task();
        task.setWork(work);
        task.setText(text);
        task.setStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> getTasksByWorkId(UUID workId) {
        return taskRepository.findByWorkId(workId);
    }

    @Override
    public Task updateTask(UUID id, String text) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setText(text);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task updateTaskStatus(UUID id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        // TODO: add logic to update work progress
        return taskRepository.save(task);
    }
}
