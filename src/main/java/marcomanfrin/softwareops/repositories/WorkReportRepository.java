package marcomanfrin.softwareops.repositories;

import marcomanfrin.softwareops.entities.WorkReport;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkReportRepository extends JpaRepository<@NonNull WorkReport, @NonNull UUID> {
}
