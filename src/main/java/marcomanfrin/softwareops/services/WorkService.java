package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.*;
import marcomanfrin.softwareops.enums.AssignmentRole;
import marcomanfrin.softwareops.enums.TaskStatus;
import marcomanfrin.softwareops.enums.TicketStatus;
import marcomanfrin.softwareops.enums.WorkStatus;
import marcomanfrin.softwareops.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkService implements IWorkService {

    private final WorkRepository workRepository;
    private final PlantRepository plantRepository;
    private final TicketRepository ticketRepository;
    private final WorkAssignmentService workAssignmentService;
    private final TaskRepository taskRepository;
    private final WorkReportRepository workReportRepository;

    public WorkService(WorkRepository workRepository, PlantRepository plantRepository, TicketRepository ticketRepository, WorkAssignmentService workAssignmentService, TaskRepository taskRepository, WorkReportRepository workReportRepository) {
        this.workRepository = workRepository;
        this.plantRepository = plantRepository;
        this.ticketRepository = ticketRepository;
        this.workAssignmentService = workAssignmentService;
        this.taskRepository = taskRepository;
        this.workReportRepository = workReportRepository;
    }

    @Override
    public Work createWorkFromPlant(UUID plantId) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found: " + plantId));

        WorkFromPlant work = new WorkFromPlant();
        work.setPlant(plant);
        work.setStatus(WorkStatus.DRAFT);
        work.setProgressPercent(0);
        work.setCreatedAt(LocalDateTime.now());
        work.setUpdatedAt(LocalDateTime.now());

        return workRepository.save(work);
    }

    @Override
    public Work createWorkFromTicket(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found: " + ticketId));

        WorkFromTicket work = new WorkFromTicket();
        work.setTicket(ticket);
        work.setStatus(WorkStatus.DRAFT);
        work.setProgressPercent(0);
        work.setCreatedAt(LocalDateTime.now());
        work.setUpdatedAt(LocalDateTime.now());

        // aggiorna ticket status (regola minima)
        if (ticket.getStatus() == TicketStatus.OPEN) {
            ticket.setStatus(TicketStatus.IN_PROGRESS);
            ticketRepository.save(ticket);
        }

        return workRepository.save(work);
    }

    @Override
    public Optional<Work> getWorkById(UUID id) {
        return workRepository.findById(id);
    }

    @Override
    public List<Work> getAllWorks() {
        return workRepository.findAll();
    }

    @Override
    public void deleteWork(UUID id) {
        if (!workRepository.existsById(id)) {
            throw new RuntimeException("Work not found: " + id);
        }
        workRepository.deleteById(id);
    }

    @Override
    public Work assignTechnician(UUID workId, UUID technicianId) {
        workAssignmentService.assignTechnicianToWork(workId, technicianId, AssignmentRole.MEMBER);

        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found: " + workId));

        // regola: appena assegnato → scheduled
        work.setStatus(WorkStatus.SCHEDULED);
        work.setUpdatedAt(LocalDateTime.now());

        return workRepository.save(work);
    }

    @Override
    public Work updateWorkStatus(UUID workId, WorkStatus status) {
        if (status == null) throw new IllegalArgumentException("Status cannot be null");

        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found: " + workId));

        work.setStatus(status);
        work.setUpdatedAt(LocalDateTime.now());

        // opzionale: aggiornare progress ogni volta
        refreshProgress(work);

        return workRepository.save(work);
    }

    @Override
    public Work completeWork(UUID workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found: " + workId));

        // ✅ precondizione 1: task completati
        if (!areAllTasksCompleted(workId)) {
            throw new IllegalStateException("Cannot complete work: not all tasks are DONE");
        }

        // ✅ precondizione 2: se da ticket, report presente e finalizzato
        if (work instanceof WorkFromTicket) {
            WorkReport report = workReportRepository.findFirstByWork_IdOrderByCreatedAtDesc(workId)
                    .orElseThrow(() -> new IllegalStateException("Cannot complete ticket-work: missing work report"));

            if (!report.isFinalized()) {
                throw new IllegalStateException("Cannot complete ticket-work: report not finalized");
            }
        }

        refreshProgress(work); // dovrebbe portare a 100
        work.setStatus(WorkStatus.COMPLETED);
        work.setUpdatedAt(LocalDateTime.now());

        // side-effect: se work da ticket → ticket DONE
        if (work instanceof WorkFromTicket wft) {
            Ticket t = wft.getTicket();
            if (t != null && t.getStatus() != TicketStatus.RESOLVED) {
                t.setStatus(TicketStatus.CLOSED);
                ticketRepository.save(t);
            }
        }

        return workRepository.save(work);
    }

    @Override
    public List<Work> getWorksByTechnician(UUID technicianId) {
        return workRepository.findWorksByTechnicianId(technicianId);
    }

    @Override
    public List<Work> getWorksByStatus(WorkStatus status) {
        if (status == null) throw new IllegalArgumentException("Status cannot be null");
        return workRepository.findByStatus(status);
    }

    @Override
    public List<WorkFromPlant> getWorksByPlantId(UUID plantId) {
        return workRepository.findByPlant_Id(plantId);
    }

    @Override
    public List<WorkFromTicket> getWorksByTicketId(UUID ticketId) {
        return workRepository.findByTicket_Id(ticketId);
    }

    private boolean areAllTasksCompleted(UUID workId) {
        long total = taskRepository.countByWorkId(workId);
        if (total == 0) return false;
        long done = taskRepository.countByWorkIdAndStatus(workId, TaskStatus.DONE);
        return done == total;
    }

    private void refreshProgress(Work work) {
        long total = taskRepository.countByWorkId(work.getId());
        if (total == 0) {
            work.setProgressPercent(0);
            return;
        }
        long done = taskRepository.countByWorkIdAndStatus(work.getId(), TaskStatus.DONE);
        int percent = (int) Math.round((done * 100.0) / total);
        work.setProgressPercent(Math.min(100, Math.max(0, percent)));
    }
}
