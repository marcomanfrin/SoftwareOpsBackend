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

    public UUID getTargetId() {
        return targetId;
    }

    public void setTargetId(UUID targetId) {
        this.targetId = targetId;
    }

    public AttachmentTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(AttachmentTargetType targetType) {
        this.targetType = targetType;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private UUID targetId;
}
