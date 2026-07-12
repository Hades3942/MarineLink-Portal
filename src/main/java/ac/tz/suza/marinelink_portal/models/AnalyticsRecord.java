package ac.tz.suza.marinelink_portal.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "analytics")
public class AnalyticsRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String metric; // e.g., "TOTAL_REVENUE", "FISH_VOLUME"

    private Double value;

    private LocalDateTime timestamp;
}
