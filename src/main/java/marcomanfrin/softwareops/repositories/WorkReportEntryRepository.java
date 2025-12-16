package marcomanfrin.softwareops.repositories;

import marcomanfrin.softwareops.entities.WorkReportEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkReportEntryRepository extends JpaRepository<WorkReportEntry, UUID> {
    List<WorkReportEntry> findByReportId(UUID reportId);
}
