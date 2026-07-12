package ac.tz.suza.marinelink_portal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ac.tz.suza.marinelink_portal.models.Listing;
import ac.tz.suza.marinelink_portal.models.ListingStatus;
import ac.tz.suza.marinelink_portal.repositories.ListingRepository;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;

    public ResponseEntity<?> createListing(Listing listing) {
        listing.setStatus(ListingStatus.AVAILABLE);
        listingRepository.save(listing);
        return ResponseEntity.ok("Listing created");
    }

    public ResponseEntity<?> getAllListings() {
        return ResponseEntity.ok(listingRepository.findAll());
    }

    public ResponseEntity<?> updateListing(Long id, Listing updated) {
        var listingOpt = listingRepository.findById(id);

        if (listingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Listing not found");
        }

        var listing = listingOpt.get();

        listing.setSpecies(updated.getSpecies());
        listing.setWeightKg(updated.getWeightKg());
        listing.setPricePerKg(updated.getPricePerKg());
        listing.setLocation(updated.getLocation());

        listingRepository.save(listing);

        return ResponseEntity.ok("Listing updated");
    }

    public ResponseEntity<?> deleteListing(Long id) {
        if (!listingRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Listing not found");
        }

        listingRepository.deleteById(id);
        return ResponseEntity.ok("Listing deleted");
    }
}
