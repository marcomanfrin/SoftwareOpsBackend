package marcomanfrin.softwareops.entities;

import jakarta.persistence.*;
        import marcomanfrin.softwareops.enums.AttachmentType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attachments")
public class Attachment {
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Id
    @GeneratedValue
    private UUID id;

    private String url;

    @Enumerated(EnumType.STRING)
    private AttachmentType type;

    private LocalDateTime uploadedAt;
}