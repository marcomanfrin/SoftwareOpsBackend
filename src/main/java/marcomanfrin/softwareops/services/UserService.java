package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.DTO.ChangePasswordRequest;
import marcomanfrin.softwareops.entities.AdministrativeUser;
import marcomanfrin.softwareops.entities.TechnicianUser;
import marcomanfrin.softwareops.entities.User;
import marcomanfrin.softwareops.enums.UserRole;
import marcomanfrin.softwareops.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String username,
                           String email,
                           String password,
                           String firstName,
                           String surname,
                           UserRole role,
                           String userType) {
        String u = requireNotBlank(username, "username");
        String e = requireNotBlank(email, "email").toLowerCase();
        String p = requireNotBlank(password, "password");

        if (userRepository.existsByUsernameIgnoreCase(u)) {
            throw new IllegalArgumentException("Username already exists: " + u);
        }
        if (userRepository.existsByEmailIgnoreCase(e)) {
            throw new IllegalArgumentException("Email already exists: " + e);
        }

        User user = createUserByType(userType);

        user.setUsername(u);
        user.setEmail(e);
        // TOOD: hash password
        user.setPasswordHash(p);
        user.setFirstName(firstName);
        user.setSurname(surname);
        user.setProfileImageUrl("https://ui-avatars.com/api/?name=" + firstName + "+" + surname);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        if (username == null) return Optional.empty();
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UUID id, String firstName, String surname, String profileImageUrl) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        user.setFirstName(firstName);
        user.setSurname(surname);
        user.setProfileImageUrl(profileImageUrl);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User changeUserRole(UUID id, UserRole role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User updateProfileImageUrl(UUID userId, String profileImageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        user.setProfileImageUrl(requireNotBlank(profileImageUrl, "profileImageUrl"));
        return userRepository.save(user);
    }

    @Override
    public User changePassword(UUID userId, ChangePasswordRequest req) {
        if (req == null) throw new IllegalArgumentException("Request cannot be null");

        String oldPw = requireNotBlank(req.oldPassword(), "oldPassword");
        String newPw = requireNotBlank(req.newPassword(), "newPassword");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // TODO: hash
        if (!oldPw.equals(user.getPasswordHash())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPasswordHash(newPw);
        return userRepository.save(user);
    }

    @Override
    public List<TechnicianUser> getTechnicians() {
        return userRepository.findAllTechnicians();
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        if (role == null) throw new IllegalArgumentException("Role cannot be null");
        return userRepository.findByRole(role);
    }

    private User createUserByType(String userType) {
        if ("ADMINISTRATIVE".equalsIgnoreCase(userType)) return new AdministrativeUser();
        if ("TECHNICIAN".equalsIgnoreCase(userType)) return new TechnicianUser();
        throw new IllegalArgumentException("Invalid user type: " + userType);
    }

    private String requireNotBlank(String value, String field) {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException(field + " cannot be blank");
        }
        return value.trim();
    }
}
