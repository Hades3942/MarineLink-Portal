package ac.tz.suza.marinelink_portal.services;

import ac.tz.suza.marinelink_portal.Dto.CreateTransaction;
import ac.tz.suza.marinelink_portal.models.*;
import ac.tz.suza.marinelink_portal.repositories.ListingRepository;
import ac.tz.suza.marinelink_portal.repositories.TransactionsRepository;
import ac.tz.suza.marinelink_portal.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionsRepository transactionRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    // ============================
    // CREATE TRANSACTION (Buyer Purchase)
    // ============================
    public Transactions createTransaction(CreateTransaction dto) {

        Listing listing = listingRepository.findById(dto.getListingId())
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        User buyer = userRepository.findById(dto.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        User fisher = listing.getFisher();

        double totalPrice = listing.getWeight() * listing.getPricePerKg();

        Transactions tx = new Transactions();
        tx.setListing(listing);
        tx.setBuyer(buyer);
        tx.setFisher(fisher);
        tx.setTotalPrice(totalPrice);
        tx.setStatus(TransactionStatus.PENDING);
        tx.setDate(LocalDateTime.now());

        // Update listing status
        listing.setStatus(ListingStatus.PENDING);
        listingRepository.save(listing);

        return transactionRepository.save(tx);
    }

    // ============================
    // GET FISHER TRANSACTIONS
    // ============================
    public List<Transactions> getFisherTransactions(Long fisherId) {
        User fisher = userRepository.findById(fisherId)
                .orElseThrow(() -> new RuntimeException("Fisher not found"));

        return transactionRepository.findByFisher(fisher);
    }

    // ============================
    // GET BUYER TRANSACTIONS
    // ============================
    public List<Transactions> getBuyerTransactions(Long buyerId) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        return transactionRepository.findByBuyer(buyer);
    }

    // ============================
    // GET ALL TRANSACTIONS (Admin + Regulator)
    // ============================
    public List<Transactions> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // ============================
    // UPDATE STATUS (Admin or Fisher)
    // ============================
    public Transactions updateStatus(Long id, TransactionStatus status) {
        Transactions tx = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        tx.setStatus(status);

        // If completed → mark listing as SOLD
        if (status == TransactionStatus.COMPLETED) {
            Listing listing = tx.getListing();
            listing.setStatus(ListingStatus.SOLD);
            listingRepository.save(listing);
        }

        return transactionRepository.save(tx);
    }

    // ============================
    // FISHER REPORTS
    // ============================
    public Map<String, Object> getFisherStats(Long fisherId) {
        Map<String, Object> stats = new HashMap<>();

        List<String> months = Arrays.asList("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
        List<Double> revenue = new ArrayList<>();
        List<Long> orders = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            revenue.add(Optional.ofNullable(transactionRepository.getMonthlyRevenue(fisherId, i)).orElse(0.0));
            orders.add(Optional.ofNullable(transactionRepository.countMonthlyTransactions(i)).orElse(0L));
        }

        long pending = transactionRepository.findByFisher(
                userRepository.findById(fisherId).get()
        ).stream().filter(t -> t.getStatus() == TransactionStatus.PENDING).count();

        long completed = transactionRepository.findByFisher(
                userRepository.findById(fisherId).get()
        ).stream().filter(t -> t.getStatus() == TransactionStatus.COMPLETED).count();

        stats.put("months", months);
        stats.put("revenue", revenue);
        stats.put("orders", orders);
        stats.put("pending", pending);
        stats.put("completed", completed);

        return stats;
    }

    // ============================
    // BUYER REPORTS
    // ============================
    public Map<String, Object> getBuyerStats(Long buyerId) {
        Map<String, Object> stats = new HashMap<>();

        List<String> months = Arrays.asList("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
        List<Double> spending = new ArrayList<>();
        List<Long> orders = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            spending.add(Optional.ofNullable(transactionRepository.getMonthlyRevenue(buyerId, i)).orElse(0.0));
            orders.add(Optional.ofNullable(transactionRepository.getMonthlyOrders(buyerId, i)).orElse(0L));
        }

        long pending = transactionRepository.findByBuyer(
                userRepository.findById(buyerId).get()
        ).stream().filter(t -> t.getStatus() == TransactionStatus.PENDING).count();

        long completed = transactionRepository.findByBuyer(
                userRepository.findById(buyerId).get()
        ).stream().filter(t -> t.getStatus() == TransactionStatus.COMPLETED).count();

        stats.put("months", months);
        stats.put("spending", spending);
        stats.put("orders", orders);
        stats.put("pending", pending);
        stats.put("completed", completed);

        return stats;
    }
}
