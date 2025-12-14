package marcomanfrin.softwareops.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrative_users")
public class AdministrativeUser extends User {
}
