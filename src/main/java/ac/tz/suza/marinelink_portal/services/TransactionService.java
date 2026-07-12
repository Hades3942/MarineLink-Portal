package ac.tz.suza.marinelink_portal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ac.tz.suza.marinelink_portal.models.Listing;
import ac.tz.suza.marinelink_portal.models.ListingStatus;
import ac.tz.suza.marinelink_portal.models.Transactions;
import ac.tz.suza.marinelink_portal.repositories.ListingRepository;
import ac.tz.suza.marinelink_portal.repositories.TransactionsRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionsRepository transactionRepository;
    private final ListingRepository listingRepository;

    public ResponseEntity<?> purchase(Long listingId, Long buyerId) {

        var listingOpt = listingRepository.findById(listingId);

        if (listingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Listing not found");
        }

        Listing listing = listingOpt.get();

        if (listing.getStatus() == ListingStatus.SOLD) {
            return ResponseEntity.badRequest().body("Listing already sold");
        }

        double totalPrice = listing.getWeightKg() * listing.getPricePerKg();

        Transactions tx = new Transactions();
        tx.setListingId(listingId);
        tx.setBuyerId(buyerId);
        tx.setFisherId(listing.getFisherId());
        tx.setTotalPrice(totalPrice);
        tx.setPaymentStatus("PAID"); // Simulated payment
        tx.setTimestamp(LocalDateTime.now());

        transactionRepository.save(tx);

        listing.setStatus(ListingStatus.SOLD);
        listingRepository.save(listing);

        return ResponseEntity.ok(tx);
    }

    public ResponseEntity<?> getBuyerHistory(Long buyerId) {
        return ResponseEntity.ok(transactionRepository.findByBuyerId(buyerId));
    }

    public ResponseEntity<?> getFisherHistory(Long fisherId) {
        return ResponseEntity.ok(transactionRepository.findByFisherId(fisherId));
    }
}
