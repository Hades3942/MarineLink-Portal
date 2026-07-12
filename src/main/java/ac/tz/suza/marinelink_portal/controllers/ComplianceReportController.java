package ac.tz.suza.marinelink_portal.controllers;

import ac.tz.suza.marinelink_portal.models.ComplianceReport;
import ac.tz.suza.marinelink_portal.services.ComplianceReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ComplianceReportController {

    private final ComplianceReportService reportService;

    public ComplianceReportController(ComplianceReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/daily")
    public ComplianceReport generateDaily(@RequestParam String admin) {
        return reportService.generateDailyReport(admin);
    }

    @PostMapping("/weekly")
    public ComplianceReport generateWeekly(@RequestParam String admin) {
        return reportService.generateWeeklyReport(admin);
    }

    @PostMapping("/monthly")
    public ComplianceReport generateMonthly(@RequestParam String admin) {
        return reportService.generateMonthlyReport(admin);
    }
}
