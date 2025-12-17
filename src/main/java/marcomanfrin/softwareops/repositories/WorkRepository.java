package marcomanfrin.softwareops.repositories;

import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.entities.WorkFromPlant;
import marcomanfrin.softwareops.entities.WorkFromTicket;
import marcomanfrin.softwareops.enums.WorkStatus;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkRepository extends JpaRepository<@NonNull Work, @NonNull UUID> {
    List<Work> findByStatus(WorkStatus status);

    // storico / tracciabilità
    List<WorkFromPlant> findByPlant_Id(UUID plantId);
    List<WorkFromTicket> findByTicket_Id(UUID ticketId);

    // “i miei lavori” via assignments (JPQL)
    @Query("""
        SELECT wa.work
        FROM WorkAssignment wa
        WHERE wa.user.id = :technicianId
    """)
    List<Work> findWorksByTechnicianId(@Param("technicianId") UUID technicianId);
}
