package marcomanfrin.softwareops.services;

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
    public User createUser(String username, String email, String password, String firstName, String surname, String profileImageUrl, UserRole role, String userType) {
        User user;
        if ("ADMINISTRATIVE".equalsIgnoreCase(userType)) {
            user = new AdministrativeUser();
        } else if ("TECHNICIAN".equalsIgnoreCase(userType)) {
            user = new TechnicianUser();
        } else {
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(password); // Assuming password is already hashed
        user.setFirstName(firstName);
        user.setSurname(surname);
        user.setProfileImageUrl(profileImageUrl);
        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UUID id, String firstName, String surname, String profileImageUrl) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(firstName);
        user.setSurname(surname);
        user.setProfileImageUrl(profileImageUrl);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User changeUserRole(UUID id, UserRole role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        return userRepository.save(user);
    }
}
