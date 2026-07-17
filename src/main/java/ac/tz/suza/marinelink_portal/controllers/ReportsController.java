package ac.tz.suza.marinelink_portal.controllers;

import ac.tz.suza.marinelink_portal.services.ReportsService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;

    // ADMIN REPORTS
    @GetMapping("/admin")
    public Map<String, Object> getAdminReports() {
        return reportsService.getAdminReports();
    }

    // REGULATOR REPORTS
    @GetMapping("/regulator")
    public Map<String, Object> getRegulatorReports() {
        return reportsService.getRegulatorReports();
    }

    // FISHER REPORTS
    @GetMapping("/fisher/{id}")
    public Map<String, Object> getFisherReports(@PathVariable Long id) {
        return reportsService.getFisherReports(id);
    }

    // BUYER REPORTS
    @GetMapping("/buyer/{id}")
    public Map<String, Object> getBuyerReports(@PathVariable Long id) {
        return reportsService.getBuyerReports(id);
    }
}
