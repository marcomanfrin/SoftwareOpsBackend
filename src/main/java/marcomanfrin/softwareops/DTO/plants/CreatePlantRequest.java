package marcomanfrin.softwareops.DTO.plants;

import java.util.UUID;

public record CreatePlantRequest(String name, String notes, String orderNumber, UUID primaryClientId, UUID finalClientId) {
}
