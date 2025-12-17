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

    Optional<WorkReport> getWorkReportByWorkId(UUID workId);

    WorkReportEntry addWorkReportEntry(UUID reportId, UUID taskId, BigDecimal hours);
    WorkReportEntry updateWorkReportEntry(UUID entryId, BigDecimal hours);
    void removeWorkReportEntry(UUID entryId);

    WorkReport calculateTotalHours(UUID reportId);
    WorkReport calculateAndPersistTotalHours(UUID workId);

    WorkReport finalizeWorkReport(UUID workId);

    WorkReport invoiceWorkReport(UUID reportId);
    boolean canInvoice(UUID workId);
}
