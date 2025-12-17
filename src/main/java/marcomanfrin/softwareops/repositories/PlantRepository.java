package marcomanfrin.softwareops.repositories;

import marcomanfrin.softwareops.entities.Plant;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlantRepository extends JpaRepository<@NonNull Plant, @NonNull UUID> {
    List<Plant> findByPrimaryClient_Id(UUID clientId);
    List<Plant> findByFinalClient_Id(UUID clientId);

    // Query "multi-campo" semplice
    @Query("""
        SELECT p FROM Plant p
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(p.orderNumber) LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(p.notes) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Plant> search(@Param("q") String q);
}
