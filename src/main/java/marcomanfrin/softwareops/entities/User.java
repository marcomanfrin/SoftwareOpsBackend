package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
import marcomanfrin.softwareops.enums.UserRole;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String email;
    private String passwordHash;
    private String firstName;
    private String surname;
    private String ProfileImageUrl;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}