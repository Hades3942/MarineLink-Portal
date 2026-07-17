package ac.tz.suza.marinelink_portal.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ac.tz.suza.marinelink_portal.models.ComplianceReport;
import ac.tz.suza.marinelink_portal.repositories.ComplianceReportRepository;
import ac.tz.suza.marinelink_portal.repositories.UserRepository;
import ac.tz.suza.marinelink_portal.repositories.ListingRepository;
import ac.tz.suza.marinelink_portal.models.User;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final DashboardAnalyticsService analyticsService;
    private final ComplianceReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    // ============================
    // MAIN DASHBOARD SUMMARY
    // ============================
    public Map<String, Object> getDashboardSummary() {

        Map<String, Object> summary = new HashMap<>();

        // REQUIRED BY FRONTEND
        summary.put("totalUsers", userRepository.count());
        summary.put("totalListings", listingRepository.count());

        // ALREADY EXISTING ANALYTICS
        summary.put("totalRevenue", analyticsService.getTotalRevenue());
        summary.put("totalTransactions", analyticsService.getTotalTransactions());
        summary.put("speciesDistribution", analyticsService.getSpeciesDistribution());
        summary.put("dailyRevenueTrend", analyticsService.getDailyRevenueTrend());
        summary.put("fisherPerformance", analyticsService.getFisherPerformance());
        summary.put("buyerActivity", analyticsService.getBuyerActivity());

        return summary;
    }

    // ============================
    // GET ALL USERS (ADMIN PAGE)
    // ============================
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ============================
    // COMPLIANCE REPORT HISTORY
    // ============================
    public Iterable<ComplianceReport> getReportHistory() {
        return reportRepository.findAll();
    }

  // UPDATE USER (ALL FIELDS)
    public User updateUser(Long id, User updated) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setZanzibarId(updated.getZanzibarId());
        user.setFullName(updated.getFullName());
        user.setEmail(updated.getEmail());
        user.setUsername(updated.getUsername());
        user.setRole(updated.getRole());

        return userRepository.save(user);
    }

    // ============================
    // DELETE USER
    // ============================
  public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
