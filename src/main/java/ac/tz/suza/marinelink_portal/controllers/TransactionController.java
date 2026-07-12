package ac.tz.suza.marinelink_portal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ac.tz.suza.marinelink_portal.services.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@CrossOrigin
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(
            @RequestParam Long listingId,
            @RequestParam Long buyerId
    ) {
        return transactionService.purchase(listingId, buyerId);
    }

    @GetMapping("/buyer/{id}")
    public ResponseEntity<?> buyerHistory(@PathVariable Long id) {
        return transactionService.getBuyerHistory(id);
    }

    @GetMapping("/fisher/{id}")
    public ResponseEntity<?> fisherHistory(@PathVariable Long id) {
        return transactionService.getFisherHistory(id);
    }
}
