package ac.tz.suza.marinelink_portal.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "compliance_reports")
public class ComplianceReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String period; // DAILY, WEEKLY, MONTHLY

    @Column(columnDefinition = "TEXT")
    private String summaryJson; // JSON summary of analytics

    private LocalDateTime generatedAt;

    private String generatedBy; // admin username
}
