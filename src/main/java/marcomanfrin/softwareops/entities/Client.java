package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
import marcomanfrin.softwareops.enums.ClientType;

import java.util.UUID;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ClientType type;
}