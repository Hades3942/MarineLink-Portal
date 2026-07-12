package ac.tz.suza.marinelink_portal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ac.tz.suza.marinelink_portal.models.Listing;
import ac.tz.suza.marinelink_portal.services.ListingService;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
@CrossOrigin
public class ListingController {

    private final ListingService listingService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Listing listing) {
        return listingService.createListing(listing);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return listingService.getAllListings();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Listing listing) {
        return listingService.updateListing(id, listing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return listingService.deleteListing(id);
    }
}
