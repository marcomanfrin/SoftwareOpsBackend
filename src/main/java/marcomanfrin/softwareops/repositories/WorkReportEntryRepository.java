package marcomanfrin.softwareops.repositories;

import marcomanfrin.softwareops.entities.WorkReportEntry;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkReportEntryRepository extends JpaRepository<@NonNull WorkReportEntry, @NonNull UUID> {
}
