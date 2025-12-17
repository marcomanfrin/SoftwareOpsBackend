package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.Attachment;
import marcomanfrin.softwareops.entities.AttachmentLink;
import marcomanfrin.softwareops.enums.AttachmentTargetType;
import marcomanfrin.softwareops.enums.AttachmentType;

import java.util.List;
import java.util.UUID;

public interface IAttachmentService {
    Attachment createAttachment(String url, AttachmentType type);
    AttachmentLink linkAttachment(UUID attachmentId, AttachmentTargetType targetType, UUID targetId);
    AttachmentLink createAndLink(String url, AttachmentType type, AttachmentTargetType targetType, UUID targetId);
    List<Attachment> getAttachments(AttachmentTargetType targetType, UUID targetId);
    void unlink(UUID linkId);
    void deleteAttachment(UUID attachmentId);
}
