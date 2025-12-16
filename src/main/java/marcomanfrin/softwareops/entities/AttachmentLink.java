package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
import marcomanfrin.softwareops.enums.AttachmentTargetType;

import java.util.UUID;

@Entity
@Table(name = "attachment_links")
public class AttachmentLink {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Attachment attachment;

    @Enumerated(EnumType.STRING)
    private AttachmentTargetType targetType;

    private UUID targetId;
}
