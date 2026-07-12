package ac.tz.suza.marinelink_portal.services;

import ac.tz.suza.marinelink_portal.models.Listing;
import ac.tz.suza.marinelink_portal.models.Transactions;
import ac.tz.suza.marinelink_portal.repositories.ListingRepository;
import ac.tz.suza.marinelink_portal.repositories.TransactionsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final TransactionsRepository transactionsRepository;
    private final ListingRepository listingRepository;

    public AnalyticsService(TransactionsRepository transactionsRepository,
                            ListingRepository listingRepository) {
        this.transactionsRepository = transactionsRepository;
        this.listingRepository = listingRepository;
    }

    // ============================
    // TOTAL REVENUE
    // ============================
    public Double getTotalRevenue() {
        return transactionsRepository.findAll()
                .stream()
                .mapToDouble(Transactions::getTotalPrice)
                .sum();
    }

    // ============================
    // TOTAL TRANSACTIONS
    // ============================
    public Long getTotalTransactions() {
        return transactionsRepository.count();
    }

    // ============================
    // SPECIES DISTRIBUTION (Pie Chart)
    // ============================
    public Map<String, Long> getSpeciesDistribution() {
        List<Listing> listings = listingRepository.findAll();
        Map<String, Long> distribution = new HashMap<>();

        for (Listing listing : listings) {
            distribution.merge(listing.getSpecies(), 1L, Long::sum);
        }

        return distribution;
    }

    // ============================
    // DAILY REVENUE TREND (Line Chart)
    // ============================
    public Map<LocalDate, Double> getDailyRevenueTrend() {
        Map<LocalDate, Double> trend = new HashMap<>();

        for (Transactions t : transactionsRepository.findAll()) {
            LocalDate date = t.getTimestamp().toLocalDate();
            trend.merge(date, t.getTotalPrice(), Double::sum);
        }

        return trend;
    }

    // ============================
    // FISHER PERFORMANCE
    // ============================
    public Map<Long, Double> getFisherPerformance() {
        Map<Long, Double> performance = new HashMap<>();

        for (Transactions t : transactionsRepository.findAll()) {
            performance.merge(t.getFisherId(), t.getTotalPrice(), Double::sum);
        }

        return performance;
    }

    // ============================
    // BUYER ACTIVITY
    // ============================
    public Map<Long, Long> getBuyerActivity() {
        Map<Long, Long> activity = new HashMap<>();

        for (Transactions t : transactionsRepository.findAll()) {
            activity.merge(t.getBuyerId(), 1L, Long::sum);
        }

        return activity;
    }
}
