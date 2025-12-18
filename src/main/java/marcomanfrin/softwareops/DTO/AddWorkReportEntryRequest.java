package marcomanfrin.softwareops.DTO;

import java.math.BigDecimal;
import java.util.UUID;

public record AddWorkReportEntryRequest (UUID taskId, BigDecimal hours) {}
