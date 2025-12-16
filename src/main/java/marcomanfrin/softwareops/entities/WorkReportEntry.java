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

    @ManyToOne
    private WorkReport report;

    @ManyToOne
    private Task task;

    private BigDecimal hours;
}