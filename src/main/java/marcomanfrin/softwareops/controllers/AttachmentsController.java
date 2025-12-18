package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.DTO.CreateAttachmentRequest;
import marcomanfrin.softwareops.entities.Attachment;
import marcomanfrin.softwareops.entities.AttachmentLink;
import marcomanfrin.softwareops.enums.AttachmentTargetType;
import marcomanfrin.softwareops.services.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("") // Resetting base path to define full paths in methods
public class AttachmentsController {

    @Autowired
    private IAttachmentService attachmentService;

    @PostMapping("/attachments")
    public AttachmentLink createAndLinkAttachment(@RequestBody CreateAttachmentRequest request) {
        return attachmentService.createAndLink(
                request.url(),
                request.type(),
                request.targetType(),
                request.targetId()
        );
    }

    @GetMapping("/{targetType}/{targetId}/attachments")
    public List<Attachment> getAttachments(
            @PathVariable AttachmentTargetType targetType,
            @PathVariable UUID targetId) {
        return attachmentService.getAttachments(targetType, targetId);
    }

    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable UUID attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/attachment-links/{linkId}")
    public ResponseEntity<Void> unlinkAttachment(@PathVariable UUID linkId) {
        attachmentService.unlink(linkId);
        return ResponseEntity.noContent().build();
    }
}
