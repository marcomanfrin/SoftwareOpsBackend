package marcomanfrin.softwareops.services;

import marcomanfrin.softwareops.entities.*;
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
                .orElseThrow(() -> new RuntimeException("Work not found"));
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        WorkReport workReport = new WorkReport();
        workReport.setWork(work);
        workReport.setTicket(ticket);
        workReport.setCreatedAt(LocalDateTime.now());
        workReport.setTotalHours(BigDecimal.ZERO);
        workReport.setInvoiced(false);

        return workReportRepository.save(workReport);
    }

    @Override
    public Optional<WorkReport> getWorkReportById(UUID id) {
        return workReportRepository.findById(id);
    }

    @Override
    public List<WorkReport> getWorkReportsByWorkId(UUID workId) {
        return workReportRepository.findByWorkId(workId);
    }

    @Override
    public WorkReportEntry addWorkReportEntry(UUID reportId, UUID taskId, BigDecimal hours) {
        WorkReport report = workReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Work report not found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        WorkReportEntry entry = new WorkReportEntry();
        entry.setReport(report);
        entry.setTask(task);
        entry.setHours(hours);

        return workReportEntryRepository.save(entry);
    }

    @Override
    public WorkReport calculateTotalHours(UUID reportId) {
        WorkReport report = workReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Work report not found"));

        List<WorkReportEntry> entries = workReportEntryRepository.findByReportId(reportId);
        BigDecimal totalHours = entries.stream()
                .map(WorkReportEntry::getHours)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        report.setTotalHours(totalHours);
        return workReportRepository.save(report);
    }

    @Override
    public WorkReport invoiceWorkReport(UUID reportId) {
        WorkReport report = workReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Work report not found"));
        report.setInvoiced(true);
        report.setInvoicedAt(LocalDateTime.now());
        return workReportRepository.save(report);
    }
}
