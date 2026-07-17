package ac.tz.suza.marinelink_portal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "fisher_id")
    private User fisher;

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime date;
}
