package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
import marcomanfrin.softwareops.enums.AttachmentType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue
    private UUID id;

    private String url;

    @Enumerated(EnumType.STRING)
    private AttachmentType type;

    private LocalDateTime uploadedAt;
}