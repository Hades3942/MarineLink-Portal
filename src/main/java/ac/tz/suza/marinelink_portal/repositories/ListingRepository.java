package ac.tz.suza.marinelink_portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ac.tz.suza.marinelink_portal.models.Listing;
import ac.tz.suza.marinelink_portal.models.ListingStatus;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findBySpeciesContainingIgnoreCase(String species);

    List<Listing> findByLocation(String location);

    List<Listing> findByStatus(ListingStatus status);

    List<Listing> findByFisherId(Long fisherId);
}
