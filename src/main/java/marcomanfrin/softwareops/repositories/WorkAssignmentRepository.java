package marcomanfrin.softwareops.repositories;

import marcomanfrin.softwareops.entities.WorkAssignment;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkAssignmentRepository extends JpaRepository<@NonNull WorkAssignment, @NonNull UUID> {
}
