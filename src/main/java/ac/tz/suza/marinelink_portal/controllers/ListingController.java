package ac.tz.suza.marinelink_portal.controllers;

import ac.tz.suza.marinelink_portal.Dto.CreateListing;
import ac.tz.suza.marinelink_portal.models.Listing;
import ac.tz.suza.marinelink_portal.models.ListingStatus;
import ac.tz.suza.marinelink_portal.services.ListingService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    // CREATE LISTING (Fisher)
    @PostMapping
    public ResponseEntity<?> createListing(@RequestBody CreateListing dto) {
        return ResponseEntity.ok(listingService.createListing(dto));
    }

    // GET ALL LISTINGS (Admin + Regulator)
    @GetMapping
    public List<Listing> getAllListings() {
        return listingService.getAllListings();
    }

    // GET FISHER LISTINGS
    @GetMapping("/fisher/{id}")
    public List<Listing> getFisherListings(@PathVariable Long id) {
        return listingService.getFisherListings(id);
    }

    // GET AVAILABLE LISTINGS (Buyer)
    @GetMapping("/available")
    public List<Listing> getAvailableListings() {
        return listingService.getAvailableListings();
    }

    // UPDATE STATUS (Purchase)
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam ListingStatus status) {

        return ResponseEntity.ok(listingService.updateListingStatus(id, status));
    }
}
