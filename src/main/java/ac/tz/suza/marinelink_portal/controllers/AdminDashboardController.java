package ac.tz.suza.marinelink_portal.controllers;

import ac.tz.suza.marinelink_portal.models.ComplianceReport;
import ac.tz.suza.marinelink_portal.repositories.UserRepository;
import ac.tz.suza.marinelink_portal.services.AdminDashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;
    private final UserRepository userRepository;
    
    public AdminDashboardController(AdminDashboardService dashboardService,
                                    UserRepository userRepository) {
        this.dashboardService = dashboardService;
        this.userRepository = userRepository; 
    }

    // ============================
    // MAIN DASHBOARD SUMMARY
    // ============================
    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        return dashboardService.getDashboardSummary();
    }

    // ============================
    // COMPLIANCE REPORT HISTORY
    // ============================
    @GetMapping("/reports")
    public Iterable<ComplianceReport> getReports() {
        return dashboardService.getReportHistory();
    }

    // ============================
    // VIEW ALL REGISTERED USERS
    // ============================
    @GetMapping("/users")
    public Iterable<?> getAllUsers() {
        return userRepository.findAll();
    }
}
