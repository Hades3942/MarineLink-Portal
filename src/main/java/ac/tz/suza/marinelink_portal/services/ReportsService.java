package ac.tz.suza.marinelink_portal.services;

import ac.tz.suza.marinelink_portal.models.Role;
import ac.tz.suza.marinelink_portal.repositories.ListingRepository;
import ac.tz.suza.marinelink_portal.repositories.TransactionsRepository;
import ac.tz.suza.marinelink_portal.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportsService {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final TransactionsRepository transactionRepository;

    private final List<String> months = Arrays.asList(
            "Jan","Feb","Mar","Apr","May","Jun",
            "Jul","Aug","Sep","Oct","Nov","Dec"
    );

    // ============================
    // ADMIN REPORTS
    // ============================
    public Map<String, Object> getAdminReports() {

        Map<String, Object> data = new HashMap<>();

        List<Double> revenue = new ArrayList<>();
        List<Long> listings = new ArrayList<>();
        List<Long> transactions = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            revenue.add((Double) Optional.ofNullable(transactionRepository.sumRevenueByMonth(i)).orElse(0.0));
            listings.add(Optional.ofNullable(listingRepository.countListingsByMonth(i)).orElse(0L));
            transactions.add(Optional.ofNullable(transactionRepository.countMonthlyTransactions(i)).orElse(0L));
        }

        long pending = transactionRepository.countPending();
        long completed = transactionRepository.countCompleted();

        Map<String, Long> roles = new HashMap<>();
        roles.put("admin", userRepository.countByRole(Role.ADMIN));
        roles.put("fisher", userRepository.countByRole(Role.FISHER));
        roles.put("buyer", userRepository.countByRole(Role.BUYER));
        roles.put("regulator", userRepository.countByRole(Role.REGULATOR));

        data.put("months", months);
        data.put("revenue", revenue);
        data.put("listings", listings);
        data.put("transactions", transactions);
        data.put("pending", pending);
        data.put("completed", completed);
        data.put("roles", roles);

        return data;
    }

    // ============================
    // REGULATOR REPORTS (same as admin)
    // ============================
    public Map<String, Object> getRegulatorReports() {
        return getAdminReports();
    }

    // ============================
    // FISHER REPORTS
    // ============================
    public Map<String, Object> getFisherReports(Long fisherId) {

        Map<String, Object> data = new HashMap<>();

        List<Double> revenue = new ArrayList<>();
        List<Long> listings = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            revenue.add(Optional.ofNullable(transactionRepository.getMonthlyRevenue(fisherId, i)).orElse(0.0));
            listings.add(Optional.ofNullable(listingRepository.countListingsByFisherMonth(fisherId, i)).orElse(0L));
        }

        long pending = transactionRepository.countPendingByFisher(fisherId);
        long completed = transactionRepository.countCompletedByFisher(fisherId);

        data.put("months", months);
        data.put("revenue", revenue);
        data.put("listings", listings);
        data.put("pending", pending);
        data.put("completed", completed);

        return data;
    }

    // ============================
    // BUYER REPORTS
    // ============================
    public Map<String, Object> getBuyerReports(Long buyerId) {

        Map<String, Object> data = new HashMap<>();

        List<Double> spending = new ArrayList<>();
        List<Long> orders = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            spending.add(Optional.ofNullable(transactionRepository.getMonthlySpending(buyerId, i)).orElse(0.0));
            orders.add(Optional.ofNullable(transactionRepository.getMonthlyOrders(buyerId, i)).orElse(0L));
        }

        long pending = transactionRepository.countPendingByBuyer(buyerId);
        long completed = transactionRepository.countCompletedByBuyer(buyerId);

        data.put("months", months);
        data.put("spending", spending);
        data.put("orders", orders);
        data.put("pending", pending);
        data.put("completed", completed);

        return data;
    }
}
