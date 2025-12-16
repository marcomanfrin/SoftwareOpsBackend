package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.enums.WorkStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IWorkService {
    Work createWorkFromPlant(UUID plantId);
    Work createWorkFromTicket(UUID ticketId);
    Optional<Work> getWorkById(UUID id);
    List<Work> getAllWorks();
    void deleteWork(UUID id);
    Work assignTechnician(UUID workId, UUID technicianId);
    Work updateWorkStatus(UUID workId, WorkStatus status);

    // da aggiungere:
    // completeWork(UUID workId) -> verifica precondizioni (tasks completati, report ok se serve) e porta il work a DONE.
    // getWorksByTechnician -> necessario per “dashboard tecnico / i miei lavori”.
    // getWorksByStatus(WorkStatus status)
    // getWorksByPlantId(UUID plantId) e getWorksByTicketId(UUID ticketId) -> utile per storico e tracciabilità.

}
