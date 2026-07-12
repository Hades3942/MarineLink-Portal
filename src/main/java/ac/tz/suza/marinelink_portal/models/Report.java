package ac.tz.suza.marinelink_portal.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // DAILY, WEEKLY, MONTHLY, COMPLIANCE

    private String generatedBy; // admin username

    @Column(columnDefinition = "TEXT")
    private String summary; // JSON or text summary

    private LocalDateTime generatedAt;
}
