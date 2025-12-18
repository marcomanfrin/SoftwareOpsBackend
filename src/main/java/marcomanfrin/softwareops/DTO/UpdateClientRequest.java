package marcomanfrin.softwareops.DTO;

import marcomanfrin.softwareops.enums.ClientType;

public record UpdateClientRequest(String name, ClientType type) {
}
