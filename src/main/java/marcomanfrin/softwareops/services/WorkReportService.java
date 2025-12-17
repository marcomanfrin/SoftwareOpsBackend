package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.*;
import marcomanfrin.softwareops.enums.WorkStatus;
import marcomanfrin.softwareops.repositories.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkReportService implements IWorkReportService {

    private final WorkReportRepository workReportRepository;
    private final WorkRepository workRepository;
    private final TicketRepository ticketRepository;
    private final TaskRepository taskRepository;
    private final WorkReportEntryRepository workReportEntryRepository;

    public WorkReportService(WorkReportRepository workReportRepository, WorkRepository workRepository, TicketRepository ticketRepository, TaskRepository taskRepository, WorkReportEntryRepository workReportEntryRepository) {
        this.workReportRepository = workReportRepository;
        this.workRepository = workRepository;
        this.ticketRepository = ticketRepository;
        this.taskRepository = taskRepository;
        this.workReportEntryRepository = workReportEntryRepository;
    }

    @Override
    public WorkReport createWorkReport(UUID workId, UUID ticketId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found: " + workId));
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found: " + ticketId));

        WorkReport report = new WorkReport();
        report.setWork(work);
        report.setTicket(ticket);
        report.setCreatedAt(LocalDateTime.now());
        report.setTotalHours(BigDecimal.ZERO);
        report.setInvoiced(false);
        report.setFinalized(false);
        report.setFinalizedAt(null);

        return workReportRepository.save(report);
    }

    @Override
    public Optional<WorkReport> getWorkReportById(UUID id) {
        return workReportRepository.findById(id);
    }

    @Override
    public List<WorkReport> getWorkReportsByWorkId(UUID workId) {
        return workReportRepository.findByWork_Id(workId);
    }

    @Override
    public Optional<WorkReport> getWorkReportByWorkId(UUID workId) {
        return workReportRepository.findFirstByWork_IdOrderByCreatedAtDesc(workId);
    }

    @Override
    public WorkReportEntry addWorkReportEntry(UUID reportId, UUID taskId, BigDecimal hours) {
        if (hours == null || hours.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Hours must be > 0");
        }

        WorkReport report = workReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Work report not found: " + reportId));

        if (report.isFinalized()) {
            throw new IllegalStateException("Report is finalized, cannot add entries");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found: " + taskId));

        WorkReportEntry entry = new WorkReportEntry();
        entry.setReport(report);
        entry.setTask(task);
        entry.setHours(hours);

        WorkReportEntry saved = workReportEntryRepository.save(entry);
        recalc(reportId);
        return saved;
    }

    @Override
    public WorkReportEntry updateWorkReportEntry(UUID entryId, BigDecimal hours) {
        if (hours == null || hours.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Hours must be > 0");
        }

        WorkReportEntry entry = workReportEntryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found: " + entryId));

        WorkReport report = entry.getReport();
        if (report.isFinalized()) {
            throw new IllegalStateException("Report is finalized, cannot update entries");
        }

        entry.setHours(hours);
        WorkReportEntry saved = workReportEntryRepository.save(entry);
        recalc(report.getId());
        return saved;
    }

    @Override
    public void removeWorkReportEntry(UUID entryId) {
        WorkReportEntry entry = workReportEntryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found: " + entryId));

        WorkReport report = entry.getReport();
        if (report.isFinalized()) {
            throw new IllegalStateException("Report is finalized, cannot remove entries");
        }

        UUID reportId = report.getId();
        workReportEntryRepository.delete(entry);
        recalc(reportId);
    }

    @Override
    public WorkReport calculateTotalHours(UUID reportId) {
        return recalc(reportId);
    }

    @Override
    public WorkReport calculateAndPersistTotalHours(UUID workId) {
        WorkReport report = getWorkReportByWorkId(workId)
                .orElseThrow(() -> new RuntimeException("No report for work: " + workId));
        return recalc(report.getId());
    }

    @Override
    public WorkReport finalizeWorkReport(UUID workId) {
        WorkReport report = getWorkReportByWorkId(workId)
                .orElseThrow(() -> new RuntimeException("No report for work: " + workId));

        if (report.isFinalized()) return report;

        recalc(report.getId());
        report.setFinalized(true);
        report.setFinalizedAt(LocalDateTime.now());
        return workReportRepository.save(report);
    }

    @Override
    public WorkReport invoiceWorkReport(UUID reportId) {
        WorkReport report = workReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Work report not found: " + reportId));

        if (report.isInvoiced()) return report;

        // opzionale: blocca se non finalizzato
        if (!report.isFinalized()) {
            throw new IllegalStateException("Cannot invoice a non-finalized report");
        }

        report.setInvoiced(true);
        report.setInvoicedAt(LocalDateTime.now());
        return workReportRepository.save(report);
    }

    @Override
    public boolean canInvoice(UUID workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found: " + workId));

        WorkReport report = getWorkReportByWorkId(workId).orElse(null);
        if (report == null) return false;

        // regola minima: report finalizzato + work DONE
        return report.isFinalized() && work.getStatus() == WorkStatus.COMPLETED && !report.isInvoiced();
    }

    private WorkReport recalc(UUID reportId) {
        WorkReport report = workReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Work report not found: " + reportId));

        BigDecimal total = workReportEntryRepository.sumHoursByReportId(reportId);
        report.setTotalHours(total);
        return workReportRepository.save(report);
    }
}
