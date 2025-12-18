package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.DTO.AddWorkReportEntryRequest;
import marcomanfrin.softwareops.DTO.UpdateWorkReportEntryRequest;
import marcomanfrin.softwareops.entities.Work;
import marcomanfrin.softwareops.entities.WorkFromTicket;
import marcomanfrin.softwareops.entities.WorkReport;
import marcomanfrin.softwareops.entities.WorkReportEntry;
import marcomanfrin.softwareops.services.IWorkReportService;
import marcomanfrin.softwareops.services.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("") // Resetting base path to define full paths in methods
public class ReportsController {

    @Autowired
    private IWorkReportService workReportService;

    @Autowired
    private IWorkService workService;

    @PostMapping("/works/{workId}/reports")
    public WorkReport createWorkReport(@PathVariable UUID workId) {
        Work work = workService.getWorkById(workId).orElse(null);
        if (work instanceof WorkFromTicket) {
            return workReportService.createWorkReport(workId, ((WorkFromTicket) work).getTicket().getId());
        }
        return workReportService.createWorkReport(workId, null);
    }

    @GetMapping("/works/{workId}/report")
    public ResponseEntity<WorkReport> getWorkReportByWorkId(@PathVariable UUID workId) {
        return workReportService.getWorkReportByWorkId(workId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/works/{workId}/report/rows")
    public ResponseEntity<WorkReportEntry> addWorkReportEntry(
            @PathVariable UUID workId,
            @RequestBody AddWorkReportEntryRequest request) {

        return workReportService.getWorkReportByWorkId(workId)
                .map(report -> {
                    WorkReportEntry entry = workReportService.addWorkReportEntry(report.getId(), request.taskId(), request.hours());
                    return ResponseEntity.ok(entry);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/works/{workId}/report/rows/{rowId}")
    public ResponseEntity<WorkReportEntry> updateWorkReportEntry(
            @PathVariable UUID workId,
            @PathVariable UUID rowId,
            @RequestBody UpdateWorkReportEntryRequest request) {
        // workId is not strictly necessary here based on the service, but it's good for URL consistency
        WorkReportEntry updatedEntry = workReportService.updateWorkReportEntry(rowId, request.hours());
        if (updatedEntry != null) {
            return ResponseEntity.ok(updatedEntry);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/works/{workId}/report/rows/{rowId}")
    public ResponseEntity<Void> removeWorkReportEntry(
            @PathVariable UUID workId,
            @PathVariable UUID rowId) {
        // workId is not strictly necessary here based on the service, but it's good for URL consistency
        workReportService.removeWorkReportEntry(rowId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/works/{workId}/report/finalize")
    public ResponseEntity<WorkReport> finalizeWorkReport(@PathVariable UUID workId) {
        WorkReport finalizedReport = workReportService.finalizeWorkReport(workId);
        if (finalizedReport != null) {
            return ResponseEntity.ok(finalizedReport);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/reports/{reportId}/invoice")
    public ResponseEntity<WorkReport> invoiceWorkReport(@PathVariable UUID reportId) {
        WorkReport invoicedReport = workReportService.invoiceWorkReport(reportId);
        if (invoicedReport != null) {
            return ResponseEntity.ok(invoicedReport);
        }
        return ResponseEntity.notFound().build();
    }
}
