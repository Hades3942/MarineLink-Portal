package ac.tz.suza.marinelink_portal.controllers;

import ac.tz.suza.marinelink_portal.Dto.CreateTransaction;
import ac.tz.suza.marinelink_portal.models.Transactions;
import ac.tz.suza.marinelink_portal.models.TransactionStatus;
import ac.tz.suza.marinelink_portal.services.TransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // BUYER PURCHASE
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransaction dto) {
        return ResponseEntity.ok(transactionService.createTransaction(dto));
    }

    // FISHER TRANSACTIONS
    @GetMapping("/fisher/{id}")
    public List<Transactions> getFisherTransactions(@PathVariable Long id) {
        return transactionService.getFisherTransactions(id);
    }

    // BUYER TRANSACTIONS
    @GetMapping("/buyer/{id}")
    public List<Transactions> getBuyerTransactions(@PathVariable Long id) {
        return transactionService.getBuyerTransactions(id);
    }

    // ADMIN + REGULATOR (ALL)
    @GetMapping
    public List<Transactions> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // UPDATE STATUS
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam TransactionStatus status) {

        return ResponseEntity.ok(transactionService.updateStatus(id, status));
    }

    // FISHER REPORTS
    @GetMapping("/fisher/stats/{id}")
    public Map<String, Object> getFisherStats(@PathVariable Long id) {
        return transactionService.getFisherStats(id);
    }

    // BUYER REPORTS
    @GetMapping("/buyer/stats/{id}")
    public Map<String, Object> getBuyerStats(@PathVariable Long id) {
        return transactionService.getBuyerStats(id);
    }
}
