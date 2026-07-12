package ac.tz.suza.marinelink_portal.controllers;

import ac.tz.suza.marinelink_portal.services.AnalyticsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/revenue")
    public Double getTotalRevenue() {
        return analyticsService.getTotalRevenue();
    }

    @GetMapping("/transactions")
    public Long getTotalTransactions() {
        return analyticsService.getTotalTransactions();
    }

    @GetMapping("/species-distribution")
    public Map<String, Long> getSpeciesDistribution() {
        return analyticsService.getSpeciesDistribution();
    }

    @GetMapping("/daily-revenue")
    public Map<LocalDate, Double> getDailyRevenueTrend() {
        return analyticsService.getDailyRevenueTrend();
    }

    @GetMapping("/fisher-performance")
    public Map<Long, Double> getFisherPerformance() {
        return analyticsService.getFisherPerformance();
    }

    @GetMapping("/buyer-activity")
    public Map<Long, Long> getBuyerActivity() {
        return analyticsService.getBuyerActivity();
    }
}
