package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.DTO.works.AssignTechnicianRequest;
import marcomanfrin.softwareops.DTO.works.UpdateWorkRequest;
import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.enums.WorkStatus;
import marcomanfrin.softwareops.services.IWorkAssignmentService;
import marcomanfrin.softwareops.services.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/works")
public class WorksController {

    @Autowired
    private IWorkService workService;

    @Autowired
    private IWorkAssignmentService workAssignmentService;

    @GetMapping
    public List<Work> getWorks(
            @RequestParam(required = false) WorkStatus status,
            @RequestParam(required = false) UUID technicianId) {

        // In-memory filtering is not efficient. A dedicated service method with pagination and filtering would be better.
        List<Work> works = workService.getAllWorks();
        Stream<Work> stream = works.stream();

        if (status != null) {
            stream = stream.filter(work -> work.getStatus() == status);
        }

        if (technicianId != null) {
            List<UUID> workIds = workAssignmentService.getAssignmentsByTechnicianId(technicianId)
                    .stream()
                    .map(assignment -> assignment.getWork().getId())
                    .toList();
            stream = stream.filter(work -> workIds.contains(work.getId()));
        }

        return stream.collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Work> getWorkById(@PathVariable UUID id) {
        return workService.getWorkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Work> updateWork(@PathVariable UUID id, @RequestBody UpdateWorkRequest request) {
        Work updatedWork = workService.updateWorkStatus(id, request.status());
        if (updatedWork != null) {
            return ResponseEntity.ok(updatedWork);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable UUID id) {
        workService.deleteWork(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/assignments")
    public ResponseEntity<Void> assignTechnician(@PathVariable UUID id, @RequestBody AssignTechnicianRequest request) {
        workAssignmentService.assignTechnicianToWork(id, request.technicianId(), request.role());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Work> completeWork(@PathVariable UUID id) {
        Work completedWork = workService.completeWork(id);
        if (completedWork != null) {
            return ResponseEntity.ok(completedWork);
        }
        return ResponseEntity.notFound().build();
    }
}
