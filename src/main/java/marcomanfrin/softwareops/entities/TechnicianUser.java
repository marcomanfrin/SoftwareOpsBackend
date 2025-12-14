package marcomanfrin.softwareops.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "technician_users")
public class TechnicianUser extends User {

}