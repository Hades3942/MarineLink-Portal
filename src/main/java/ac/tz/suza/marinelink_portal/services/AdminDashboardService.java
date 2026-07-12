package ac.tz.suza.marinelink_portal.services;

import ac.tz.suza.marinelink_portal.models.ComplianceReport;
import ac.tz.suza.marinelink_portal.repositories.ComplianceReportRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminDashboardService {

    private final AnalyticsService analyticsService;
    private final ComplianceReportRepository reportRepository;

    public AdminDashboardService(AnalyticsService analyticsService,
                                 ComplianceReportRepository reportRepository) {
        this.analyticsService = analyticsService;
        this.reportRepository = reportRepository;
    }

    // ============================
    // MAIN DASHBOARD SUMMARY
    // ============================
    public Map<String, Object> getDashboardSummary() {

        Map<String, Object> summary = new HashMap<>();

        summary.put("totalRevenue", analyticsService.getTotalRevenue());
        summary.put("totalTransactions", analyticsService.getTotalTransactions());
        summary.put("speciesDistribution", analyticsService.getSpeciesDistribution());
        summary.put("dailyRevenueTrend", analyticsService.getDailyRevenueTrend());
        summary.put("fisherPerformance", analyticsService.getFisherPerformance());
        summary.put("buyerActivity", analyticsService.getBuyerActivity());

        return summary;
    }

    // ============================
    // COMPLIANCE REPORT HISTORY
    // ============================
    public Iterable<ComplianceReport> getReportHistory() {
        return reportRepository.findAll();
    }
}
