package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.WorkReport;
import marcomanfrin.softwareops.entities.WorkReportEntry;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IWorkReportService {
    WorkReport createWorkReport(UUID workId, UUID ticketId);
    Optional<WorkReport> getWorkReportById(UUID id);
    List<WorkReport> getWorkReportsByWorkId(UUID workId);
    WorkReportEntry addWorkReportEntry(UUID reportId, UUID taskId, BigDecimal hours);
    WorkReport calculateTotalHours(UUID reportId);
    WorkReport invoiceWorkReport(UUID reportId);
}
