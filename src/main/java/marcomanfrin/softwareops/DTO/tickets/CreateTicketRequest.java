package marcomanfrin.softwareops.DTO.tickets;

import jakarta.validation.constraints.NotBlank;

public record CreateTicketRequest(@NotBlank String name, String description) {
}
