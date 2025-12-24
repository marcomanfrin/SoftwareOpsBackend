package marcomanfrin.softwareops.DTO.dashboard;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TicketPreviewDTO(
        UUID id,
        String title,
        String status,
        LocalDateTime createdAt
) {
}
