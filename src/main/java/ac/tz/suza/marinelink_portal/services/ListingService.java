package ac.tz.suza.marinelink_portal.services;

import ac.tz.suza.marinelink_portal.Dto.CreateListing;
import ac.tz.suza.marinelink_portal.models.Listing;
import ac.tz.suza.marinelink_portal.models.ListingStatus;
import ac.tz.suza.marinelink_portal.models.User;
import ac.tz.suza.marinelink_portal.repositories.ListingRepository;
import ac.tz.suza.marinelink_portal.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    // ============================
    // CREATE LISTING (Fisher)
    // ============================
    public Listing createListing(CreateListing dto) {

        User fisher = userRepository.findById(dto.getFisherId())
                .orElseThrow(() -> new RuntimeException("Fisher not found"));

        Listing listing = new Listing();
        listing.setSpecies(dto.getSpecies());
        listing.setLocation(dto.getLocation());
        listing.setWeight(dto.getWeight());
        listing.setPricePerKg(dto.getPricePerKg());
        listing.setStatus(ListingStatus.AVAILABLE);
        listing.setFisher(fisher);

        return listingRepository.save(listing);
    }

    // ============================
    // GET ALL LISTINGS (Admin + Regulator)
    // ============================
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    // ============================
    // GET FISHER LISTINGS
    // ============================
    public List<Listing> getFisherListings(Long fisherId) {
        User fisher = userRepository.findById(fisherId)
                .orElseThrow(() -> new RuntimeException("Fisher not found"));

        return listingRepository.findByFisher(fisher);
    }

    // ============================
    // GET AVAILABLE LISTINGS (Buyer)
    // ============================
    public List<Listing> getAvailableListings() {
        return listingRepository.findByStatus(ListingStatus.AVAILABLE);
    }

    // ============================
    // UPDATE LISTING STATUS (Purchase)
    // ============================
    public Listing updateListingStatus(Long id, ListingStatus status) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        listing.setStatus(status);
        return listingRepository.save(listing);
    }
}
