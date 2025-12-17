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
                .orElseThrow(() -> new RuntimeException("Work not found: " + workId));

        String validatedText = validateText(text);

        Task task = new Task();
        task.setWork(work);
        task.setText(validatedText);
        task.setStatus(TaskStatus.TODO);

        Task saved = taskRepository.save(task);
        onTasksChanged(workId);
        return saved;
    }

    @Override
    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> getTasksByWorkId(UUID workId) {
        if (!workRepository.existsById(workId)) {
            throw new RuntimeException("Work not found: " + workId);
        }
        return taskRepository.findByWorkId(workId);
    }

    @Override
    public Task updateTask(UUID id, String text) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));

        task.setText(validateText(text));

        Task saved = taskRepository.save(task);
        onTasksChanged(task.getWork().getId());
        return saved;
    }

    @Override
    public void deleteTask(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));

        UUID workId = task.getWork().getId();
        taskRepository.delete(task);
        onTasksChanged(workId);
    }

    @Override
    public Task updateTaskStatus(UUID id, TaskStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Task status cannot be null");
        }

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));

        if (task.getStatus() == status) {
            return task;
        }

        task.setStatus(status);

        Task saved = taskRepository.save(task);
        onTasksChanged(task.getWork().getId());
        return saved;
    }

    @Override
    public Task completeTask(UUID taskId) {
        return updateTaskStatus(taskId, TaskStatus.DONE);
    }

    @Override
    public List<Task> completeAllTasks(UUID workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found: " + workId));

        List<Task> tasks = taskRepository.findByWorkId(work.getId());
        for (Task t : tasks) {
            t.setStatus(TaskStatus.DONE);
        }
        List<Task> saved = taskRepository.saveAll(tasks);
        onTasksChanged(workId);
        return saved;
    }

    @Override
    public boolean areAllTasksCompleted(UUID workId) {
        if (!workRepository.existsById(workId)) {
            throw new RuntimeException("Work not found: " + workId);
        }
        long total = taskRepository.countByWorkId(workId);
        if (total == 0) return false; // decisione di dominio: nessuna task => non “completato”
        long done = taskRepository.countByWorkIdAndStatus(workId, TaskStatus.DONE);
        return done == total;
    }

    @Override
    public double getCompletionRate(UUID workId) {
        if (!workRepository.existsById(workId)) {
            throw new RuntimeException("Work not found: " + workId);
        }
        long total = taskRepository.countByWorkId(workId);
        if (total == 0) return 0.0;
        long done = taskRepository.countByWorkIdAndStatus(workId, TaskStatus.DONE);
        return (done * 100.0) / total;
    }

    @Override
    public List<Task> getTasksByStatus(UUID workId, TaskStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Task status cannot be null");
        }
        if (!workRepository.existsById(workId)) {
            throw new RuntimeException("Work not found: " + workId);
        }
        return taskRepository.findByWorkIdAndStatus(workId, status);
    }

    private String validateText(String text) {
        if (text == null || text.trim().isBlank()) {
            throw new IllegalArgumentException("Task text cannot be blank");
        }
        return text.trim();
    }

    private void onTasksChanged(UUID workId) {
        // Hook per il futuro:
        // - aggiornare una % sul Work
        // - aggiornare WorkStatus (es. se tutte DONE -> READY_FOR_REPORT)
    }
}
