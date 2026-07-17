package ac.tz.suza.marinelink_portal.services;

import ac.tz.suza.marinelink_portal.models.Listing;
import ac.tz.suza.marinelink_portal.models.Role;
import ac.tz.suza.marinelink_portal.repositories.ListingRepository;
import ac.tz.suza.marinelink_portal.repositories.TransactionsRepository;
import ac.tz.suza.marinelink_portal.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardAnalyticsService {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final TransactionsRepository transactionRepository;

    private final List<String> months = Arrays.asList(
            "Jan","Feb","Mar","Apr","May","Jun",
            "Jul","Aug","Sep","Oct","Nov","Dec"
    );

    // ============================
    // ADMIN DASHBOARD ANALYTICS
    // ============================
    public Map<String, Object> getAdminDashboard() {

        Map<String, Object> data = new HashMap<>();

        // TOTAL COUNTS
        long totalUsers = userRepository.count();
        long totalListings = listingRepository.count();
        long totalTransactions = transactionRepository.count();

        Double totalRevenue = (Double) Optional.ofNullable(
                transactionRepository.sumAllRevenue()
        ).orElse(0.0);

        // MONTHLY ANALYTICS
        List<Double> monthlyRevenue = new ArrayList<>();
        List<Long> monthlyListings = new ArrayList<>();
        List<Long> monthlyTransactions = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            monthlyRevenue.add(Optional.ofNullable(
                    transactionRepository.sumRevenueByMonth(i)
            ).orElse(0.0));

            monthlyListings.add(Optional.ofNullable(
                    listingRepository.countListingsByMonth(i)
            ).orElse(0L));

            monthlyTransactions.add(Optional.ofNullable(
                    transactionRepository.countMonthlyTransactions(i)
            ).orElse(0L));
        }

        // STATUS BREAKDOWN
        long pending = transactionRepository.countPending();
        long completed = transactionRepository.countCompleted();

        // ROLE DISTRIBUTION
        Map<String, Long> roles = new HashMap<>();
        roles.put("admin", userRepository.countByRole(Role.ADMIN));
        roles.put("fisher", userRepository.countByRole(Role.FISHER));
        roles.put("buyer", userRepository.countByRole(Role.BUYER));
        roles.put("regulator", userRepository.countByRole(Role.REGULATOR));

        // PUT DATA
        data.put("totalUsers", totalUsers);
        data.put("totalListings", totalListings);
        data.put("totalTransactions", totalTransactions);
        data.put("totalRevenue", totalRevenue);

        data.put("months", months);
        data.put("monthlyRevenue", monthlyRevenue);
        data.put("monthlyListings", monthlyListings);
        data.put("monthlyTransactions", monthlyTransactions);

        data.put("pending", pending);
        data.put("completed", completed);
        data.put("roles", roles);

        return data;
    }

    // ============================
    // FISHER DASHBOARD ANALYTICS
    // ============================
    public Map<String, Object> getFisherDashboard(Long fisherId) {

        Map<String, Object> data = new HashMap<>();

        // TOTAL LISTINGS
        long totalListings = listingRepository.countListingsByFisher(fisherId);

        // TOTAL TRANSACTIONS
        long totalTransactions = transactionRepository.countByFisher(fisherId);

        // TOTAL REVENUE
        Double totalRevenue = Optional.ofNullable(
                transactionRepository.sumRevenueByFisher(fisherId)
        ).orElse(0.0);


        List<Listing> recentListings = listingRepository.findByFisher(
        userRepository.findById(fisherId).get()
        );

        data.put("recentListings", recentListings);


        // STATUS BREAKDOWN
        long pending = transactionRepository.countPendingByFisher(fisherId);
        long completed = transactionRepository.countCompletedByFisher(fisherId);

        data.put("totalListings", totalListings);
        data.put("totalTransactions", totalTransactions);
        data.put("totalRevenue", totalRevenue);
        data.put("pending", pending);
        data.put("completed", completed);

        return data;
    }

    // ============================
    // BUYER DASHBOARD ANALYTICS
    // ============================
    public Map<String, Object> getBuyerDashboard(Long buyerId) {

        Map<String, Object> data = new HashMap<>();

        // TOTAL ORDERS
        long totalOrders = transactionRepository.countByBuyer(buyerId);

        // TOTAL SPENDING
        Double totalSpending = Optional.ofNullable(
                transactionRepository.sumSpendingByBuyer(buyerId)
        ).orElse(0.0);

        // MONTHLY ANALYTICS
        List<Double> monthlySpending = new ArrayList<>();
        List<Long> monthlyOrders = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            monthlySpending.add(Optional.ofNullable(
                    transactionRepository.getMonthlySpending(buyerId, i)
            ).orElse(0.0));

            monthlyOrders.add(Optional.ofNullable(
                    transactionRepository.getMonthlyOrders(buyerId, i)
            ).orElse(0L));
        }

        // STATUS BREAKDOWN
        long pending = transactionRepository.countPendingByBuyer(buyerId);
        long completed = transactionRepository.countCompletedByBuyer(buyerId);

        data.put("totalOrders", totalOrders);
        data.put("totalSpending", totalSpending);

        data.put("months", months);
        data.put("monthlySpending", monthlySpending);
        data.put("monthlyOrders", monthlyOrders);

        data.put("pending", pending);
        data.put("completed", completed);

        return data;
    }

    // ============================
    // REGULATOR DASHBOARD ANALYTICS
    // ============================
    public Map<String, Object> getRegulatorDashboard() {
        return getAdminDashboard(); // same analytics
    }

    public Object getTotalTransactions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalTransactions'");
    }

    public Object getTotalRevenue() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalRevenue'");
    }

    public Object getSpeciesDistribution() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpeciesDistribution'");
    }

    public Object getDailyRevenueTrend() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDailyRevenueTrend'");
    }

    public Object getFisherPerformance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFisherPerformance'");
    }

    public Object getBuyerActivity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBuyerActivity'");
    }
}
