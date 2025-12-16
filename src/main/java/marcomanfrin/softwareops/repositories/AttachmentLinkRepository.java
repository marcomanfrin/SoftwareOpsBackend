package marcomanfrin.softwareops.repositories;

import marcomanfrin.softwareops.entities.AttachmentLink;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttachmentLinkRepository extends JpaRepository<@NonNull AttachmentLink, @NonNull UUID> {
}
