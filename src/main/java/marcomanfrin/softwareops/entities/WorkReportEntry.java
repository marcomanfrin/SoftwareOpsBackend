package marcomanfrin.softwareops.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "work_report_entries")
public class WorkReportEntry {
    @Id
    @GeneratedValue
    private UUID id;

    public WorkReport getReport() {
        return report;
    }

    @ManyToOne
    private WorkReport report;
    @ManyToOne
    private Task task;
    private BigDecimal hours;

    public void setReport(WorkReport report) {
        this.report = report;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public BigDecimal getHours() {
        return hours;
    }
}