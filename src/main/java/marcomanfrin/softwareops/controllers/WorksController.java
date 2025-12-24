package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.DTO.works.AssignTechnicianRequest;
import marcomanfrin.softwareops.DTO.works.UpdateWorkRequest;
import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.enums.WorkStatus;
import marcomanfrin.softwareops.services.IWorkAssignmentService;
import marcomanfrin.softwareops.services.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/works")
public class WorksController {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;

    @Autowired
    private IWorkService workService;

    @Autowired
    private IWorkAssignmentService workAssignmentService;

    @GetMapping
    public Page<Work> getWorks(
            @RequestParam(required = false) WorkStatus status,
            @RequestParam(required = false) UUID technicianId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "" + DEFAULT_PAGE_SIZE) int size) {
        PageRequest pageRequest = buildPageRequest(page, size, Sort.by("createdAt").descending());
        return workService.getWorks(status, technicianId, pageRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Work> getWorkById(@PathVariable UUID id) {
        return workService.getWorkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private PageRequest buildPageRequest(int page, int size, Sort sort) {
        int safePage = Math.max(0, page);
        int safeSize = (size < 1 || size > MAX_PAGE_SIZE) ? DEFAULT_PAGE_SIZE : size;
        return PageRequest.of(safePage, safeSize, sort);
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
