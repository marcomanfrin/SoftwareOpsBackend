package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.DTO.ChangePasswordRequest;
import marcomanfrin.softwareops.DTO.CreateUserRequest;
import marcomanfrin.softwareops.DTO.UpdateUserRequest;
import marcomanfrin.softwareops.DTO.UpdateUserRoleRequest;
import marcomanfrin.softwareops.entities.TechnicianUser;
import marcomanfrin.softwareops.entities.User;
import marcomanfrin.softwareops.enums.UserRole;
import marcomanfrin.softwareops.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final IUserService usersService;

    public UsersController(IUserService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public List<User> getUsers() {
        return this.usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {

        return usersService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
        User updatedUser = usersService.updateUser(id, request.firstName(), request.surname(), null);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable UUID id, @RequestBody UpdateUserRoleRequest request) {
        User updatedUser = usersService.changeUserRole(id, request.role());
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable UUID id, @RequestBody ChangePasswordRequest payload) {
        usersService.changePassword(id, payload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/technicians")
    public List<TechnicianUser> getTechnicians() {
        return usersService.getTechnicians();
    }

    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable UserRole role) {
        return usersService.getUsersByRole(role);
    }

    @PatchMapping("/{id}/avatar")
    public ResponseEntity<String> uploadProfileImage(@PathVariable UUID id, @RequestParam("avatar") MultipartFile file) {
        String imageUrl = usersService.uploadProfileImage(id, file);
        return ResponseEntity.ok(imageUrl);
    }
}
