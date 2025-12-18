package marcomanfrin.softwareops.DTO;

import marcomanfrin.softwareops.enums.ClientType;

public record CreateClientRequest(String name, ClientType type) {
}
