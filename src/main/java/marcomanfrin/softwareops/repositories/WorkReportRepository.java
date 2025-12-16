package marcomanfrin.softwareops.repositories;

import marcomanfrin.softwareops.entities.WorkReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkReportRepository extends JpaRepository<WorkReport, UUID> {
    List<WorkReport> findByWorkId(UUID workId);
}
