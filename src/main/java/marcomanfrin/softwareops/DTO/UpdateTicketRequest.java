package marcomanfrin.softwareops.DTO;

import marcomanfrin.softwareops.enums.TicketStatus;

public record UpdateTicketRequest(String name, String description, TicketStatus status) {
}
