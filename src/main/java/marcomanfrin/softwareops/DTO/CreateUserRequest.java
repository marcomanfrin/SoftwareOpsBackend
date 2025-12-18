package marcomanfrin.softwareops.DTO;

import marcomanfrin.softwareops.enums.UserRole;

public record CreateUserRequest(String username, String email, String password, String firstName, String surname, UserRole role, String userType) {
}
