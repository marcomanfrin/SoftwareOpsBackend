package marcomanfrin.softwareops.services;

import jakarta.transaction.Transactional;
import marcomanfrin.softwareops.entities.Attachment;
import marcomanfrin.softwareops.entities.AttachmentLink;
import marcomanfrin.softwareops.enums.AttachmentTargetType;
import marcomanfrin.softwareops.enums.AttachmentType;
import marcomanfrin.softwareops.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService implements IAttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentLinkRepository attachmentLinkRepository;

    private final WorkRepository workRepository;
    private final PlantRepository plantRepository;
    private final TicketRepository ticketRepository;
    private final WorkReportRepository workReportRepository;

    public AttachmentService(AttachmentRepository attachmentRepository,
                             AttachmentLinkRepository attachmentLinkRepository,
                             WorkRepository workRepository,
                             PlantRepository plantRepository,
                             TicketRepository ticketRepository,
                             WorkReportRepository workReportRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentLinkRepository = attachmentLinkRepository;
        this.workRepository = workRepository;
        this.plantRepository = plantRepository;
        this.ticketRepository = ticketRepository;
        this.workReportRepository = workReportRepository;
    }

    @Override
    public Attachment createAttachment(String url, AttachmentType type) {
        if (type == null) throw new IllegalArgumentException("Attachment type cannot be null");
        String u = requireNotBlank(url, "url");

        Attachment attachment = new Attachment();
        attachment.setUrl(u);
        attachment.setType(type);
        attachment.setUploadedAt(LocalDateTime.now());

        return attachmentRepository.save(attachment);
    }

    @Override
    public AttachmentLink linkAttachment(UUID attachmentId, AttachmentTargetType targetType, UUID targetId) {
        if (targetType == null) throw new IllegalArgumentException("targetType cannot be null");
        if (targetId == null) throw new IllegalArgumentException("targetId cannot be null");

        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found: " + attachmentId));

        assertTargetExists(targetType, targetId);

        // idempotenza: evita doppio link uguale
        if (attachmentLinkRepository.existsByAttachment_IdAndTargetTypeAndTargetId(attachmentId, targetType, targetId)) {
            // ritorna il primo match
            return attachmentLinkRepository.findByAttachment_Id(attachmentId).stream()
                    .filter(l -> l.getTargetType() == targetType && targetId.equals(l.getTargetId()))
                    .findFirst()
                    .orElseThrow();
        }

        AttachmentLink link = new AttachmentLink();
        link.setAttachment(attachment);
        link.setTargetType(targetType);
        link.setTargetId(targetId);

        return attachmentLinkRepository.save(link);
    }

    @Override
    @Transactional
    public AttachmentLink createAndLink(String url, AttachmentType type, AttachmentTargetType targetType, UUID targetId) {
        Attachment attachment = createAttachment(url, type);
        return linkAttachment(attachment.getId(), targetType, targetId);
    }

    @Override
    public List<Attachment> getAttachments(AttachmentTargetType targetType, UUID targetId) {
        assertTargetExists(targetType, targetId);

        List<AttachmentLink> links = attachmentLinkRepository.findByTargetTypeAndTargetId(targetType, targetId);

        return links.stream()
                .map(AttachmentLink::getAttachment)
                .distinct()
                .toList();
    }

    @Override
    public void unlink(UUID linkId) {
        if (!attachmentLinkRepository.existsById(linkId)) {
            throw new RuntimeException("AttachmentLink not found: " + linkId);
        }
        attachmentLinkRepository.deleteById(linkId);
    }

    @Override
    @Transactional
    public void deleteAttachment(UUID attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found: " + attachmentId));

        // elimina prima i link
        List<AttachmentLink> links = attachmentLinkRepository.findByAttachment_Id(attachmentId);
        attachmentLinkRepository.deleteAll(links);

        // poi l’attachment
        attachmentRepository.delete(attachment);
    }

    private void assertTargetExists(AttachmentTargetType targetType, UUID targetId) {
        boolean exists = switch (targetType) {
            case WORK -> workRepository.existsById(targetId);
            case PLANT -> plantRepository.existsById(targetId);
            case TICKET -> ticketRepository.existsById(targetId);
            case REPORT -> workReportRepository.existsById(targetId);
        };

        if (!exists) {
            throw new RuntimeException("Target not found: " + targetType + " " + targetId);
        }
    }

    private String requireNotBlank(String value, String field) {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException(field + " cannot be blank");
        }
        return value.trim();
    }
}
