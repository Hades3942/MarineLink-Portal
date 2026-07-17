package ac.tz.suza.marinelink_portal.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String species;
    private String location;
    private double weight;
    private double pricePerKg;

    @Enumerated(EnumType.STRING)
    private ListingStatus status;

    @ManyToOne
    @JoinColumn(name = "fisher_id", nullable = false)
    private User fisher;

      @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
