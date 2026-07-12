package ac.tz.suza.marinelink_portal.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fisherId; // FK to User

    private String species;
    private double weightKg;
    private double pricePerKg;
    private String location;

    @Enumerated(EnumType.STRING)
    private ListingStatus status; // AVAILABLE, SOLD
}
