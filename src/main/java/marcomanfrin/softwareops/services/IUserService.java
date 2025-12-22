package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.DTO.ChangePasswordRequest;
import marcomanfrin.softwareops.entities.TechnicianUser;
import marcomanfrin.softwareops.entities.User;
import marcomanfrin.softwareops.enums.UserRole;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface IUserService {
    User createUser(String username, String email, String password, String firstName, String surname, UserRole role, String userType);
    Optional<User> getUserById(UUID id);
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(UUID id, String firstName, String surname, String profileImageUrl);
    void deleteUser(UUID id);
    User changeUserRole(UUID id, UserRole role);
    User updateProfileImageUrl(UUID userId, String profileImageUrl);
    User changePassword(UUID userId, ChangePasswordRequest req);
    List<TechnicianUser> getTechnicians();
    List<User> getUsersByRole(UserRole role);
    String uploadProfileImage(UUID userId, MultipartFile file);
    Optional<User> getUserByEmail(String email);
}