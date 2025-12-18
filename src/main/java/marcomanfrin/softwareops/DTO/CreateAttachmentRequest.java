package marcomanfrin.softwareops.DTO;

import marcomanfrin.softwareops.enums.AttachmentTargetType;
import marcomanfrin.softwareops.enums.AttachmentType;
import java.util.UUID;

public record CreateAttachmentRequest (String url, AttachmentType type, AttachmentTargetType targetType, UUID targetId) {}
