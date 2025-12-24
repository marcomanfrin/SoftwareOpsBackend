package marcomanfrin.softwareops.DTO.users;

import marcomanfrin.softwareops.enums.UserRole;

public record UserDTO(
        String firstName,
        String lastName,
        String email,
        String username,
        String profileImageUrl,
        UserRole role
        ) {}
