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
    private String publicId;
    private String resourceType;
    @Enumerated(EnumType.STRING)
    private AttachmentType type;
    private LocalDateTime uploadedAt;

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

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

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}