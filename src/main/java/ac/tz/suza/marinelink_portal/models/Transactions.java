package ac.tz.suza.marinelink_portal.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long listingId;
    private Long buyerId;
    private Long fisherId;

    private double totalPrice;
    private String paymentStatus; // PAID, FAILED

    private LocalDateTime timestamp;
}
