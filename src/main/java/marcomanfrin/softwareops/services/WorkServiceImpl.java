package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.*;
import marcomanfrin.softwareops.enums.AssignmentRole;
import marcomanfrin.softwareops.enums.WorkStatus;
import marcomanfrin.softwareops.repositories.PlantRepository;
import marcomanfrin.softwareops.repositories.TicketRepository;
import marcomanfrin.softwareops.repositories.WorkRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkServiceImpl implements IWorkService {

    private final WorkRepository workRepository;
    private final PlantRepository plantRepository;
    private final TicketRepository ticketRepository;
    private final WorkAssignmentService workAssignmentService;

    public WorkServiceImpl(WorkRepository workRepository, PlantRepository plantRepository, TicketRepository ticketRepository, WorkAssignmentService workAssignmentService) {
        this.workRepository = workRepository;
        this.plantRepository = plantRepository;
        this.ticketRepository = ticketRepository;
        this.workAssignmentService = workAssignmentService;
    }


    public Work createWorkFromPlant(UUID plantId) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found"));
        WorkFromPlant work = new WorkFromPlant();
        work.setPlant(plant);
        work.setStatus(WorkStatus.DRAFT);
        work.setCreatedAt(LocalDateTime.now());
        return workRepository.save(work);
    }


    public Work createWorkFromTicket(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        WorkFromTicket work = new WorkFromTicket();
        work.setTicket(ticket);
        work.setStatus(WorkStatus.DRAFT);
        work.setCreatedAt(LocalDateTime.now());
        // TODO: update ticket status
        return workRepository.save(work);
    }


    public Optional<Work> getWorkById(UUID id) {
        return workRepository.findById(id);
    }


    public List<Work> getAllWorks() {
        return workRepository.findAll();
    }


    public void deleteWork(UUID id) {
        workRepository.deleteById(id);
    }


    public Work assignTechnician(UUID workId, UUID technicianId) {
        workAssignmentService.assignTechnicianToWork(workId, technicianId, AssignmentRole.MEMBER);
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));
        work.setStatus(WorkStatus.SCHEDULED);
        return workRepository.save(work);
    }

    public Work updateWorkStatus(UUID workId, WorkStatus status) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));
        work.setStatus(status);
        work.setUpdatedAt(LocalDateTime.now());
        return workRepository.save(work);
    }
}
