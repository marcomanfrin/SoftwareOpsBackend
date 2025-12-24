package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.DTO.tasks.CreateTaskRequest;
import marcomanfrin.softwareops.DTO.tasks.UpdateTaskRequest;
import marcomanfrin.softwareops.DTO.tasks.UpdateTaskStatusRequest;
import marcomanfrin.softwareops.entities.Task;
import marcomanfrin.softwareops.enums.TaskStatus;
import marcomanfrin.softwareops.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("") // Resetting base path to define full paths in methods
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @PostMapping("/works/{workId}/tasks")
    public Task createTask(@PathVariable UUID workId, @RequestBody CreateTaskRequest request) {
        return taskService.createTask(workId, request.text());
    }

    @GetMapping("/works/{workId}/tasks")
    public List<Task> getTasksByWorkId(@PathVariable UUID workId) {
        return taskService.getTasksByWorkId(workId);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID taskId) {
        return taskService.getTaskById(taskId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID taskId, @RequestBody UpdateTaskRequest request) {
        Task updatedTask = taskService.updateTask(taskId, request.text());
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/tasks/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable UUID taskId, @RequestBody UpdateTaskStatusRequest request) {
        Task updatedTask = taskService.updateTaskStatus(taskId, request.status());
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tasks/{taskId}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable UUID taskId) {
        Task completedTask = taskService.completeTask(taskId);
        if (completedTask != null) {
            return ResponseEntity.ok(completedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/works/{workId}/tasks/complete-all")
    public List<Task> completeAllTasks(@PathVariable UUID workId) {
        return taskService.completeAllTasks(workId);
    }

    @GetMapping("/works/{workId}/tasks/completion-rate")
    public double getCompletionRate(@PathVariable UUID workId) {
        return taskService.getCompletionRate(workId);
    }

    @GetMapping("/works/{workId}/tasks/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable UUID workId, @PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(workId, status);
    }
}
