package ac.tz.suza.marinelink_portal.controllers;

import ac.tz.suza.marinelink_portal.services.DashboardAnalyticsService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardAnalyticsController {

    private final DashboardAnalyticsService dashboardService;

    // ADMIN DASHBOARD
    @GetMapping("/admin")
    public Map<String, Object> getAdminDashboard() {
        return dashboardService.getAdminDashboard();
    }

    // FISHER DASHBOARD
    @GetMapping("/fisher/{id}")
    public Map<String, Object> getFisherDashboard(@PathVariable Long id) {
        return dashboardService.getFisherDashboard(id);
    }

    // BUYER DASHBOARD
    @GetMapping("/buyer/{id}")
    public Map<String, Object> getBuyerDashboard(@PathVariable Long id) {
        return dashboardService.getBuyerDashboard(id);
    }

    // REGULATOR DASHBOARD
    @GetMapping("/regulator")
    public Map<String, Object> getRegulatorDashboard() {
        return dashboardService.getRegulatorDashboard();
    }
}
