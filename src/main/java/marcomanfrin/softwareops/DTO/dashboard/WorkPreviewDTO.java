package marcomanfrin.softwareops.DTO.dashboard;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record WorkPreviewDTO(
        UUID id,
        String title,
        boolean completed,
        LocalDateTime createdAt
) {
}
