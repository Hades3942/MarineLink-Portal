package ac.tz.suza.marinelink_portal.repositories;

import ac.tz.suza.marinelink_portal.models.Listing;
import ac.tz.suza.marinelink_portal.models.ListingStatus;
import ac.tz.suza.marinelink_portal.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    // Fisher listings
    List<Listing> findByFisher(User fisher);

    // Listings by status (AVAILABLE, PENDING, SOLD)
    @Query("SELECT l FROM Listing l WHERE l.status = :status")
    List<Listing> findByStatus(ListingStatus status);

    // Monthly listing count (Admin + Regulator)
    @Query("SELECT COUNT(l) FROM Listing l WHERE MONTH(l.createdAt) = :month")
    Long countListingsByMonth(int month);

    // Fisher monthly listing count (Fisher Reports)
    @Query("SELECT COUNT(l) FROM Listing l WHERE l.fisher.id = :fisherId AND MONTH(l.createdAt) = :month")
    Long countListingsByFisherMonth(Long fisherId, int month);

    @Query("SELECT COUNT(l) FROM Listing l WHERE l.fisher.id = :fisherId")
    Long countListingsByFisher(Long fisherId);

    

}
