package ac.tz.suza.marinelink_portal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ac.tz.suza.marinelink_portal.repositories.ComplianceReportRepository;
import ac.tz.suza.marinelink_portal.repositories.ReportsRepository;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin
public class ReportsViewerController {

    private final ComplianceReportRepository complianceRepo;
    private final ReportsRepository reportsRepo;

    @GetMapping("/all")
    public ResponseEntity<?> getAllReports() {
        var compliance = complianceRepo.findAll();
        var analytics = reportsRepo.findAll();

        return ResponseEntity.ok(
            new Object() {
                public final Object complianceReports = compliance;
                public final Object analyticsReports = analytics;
            }
        );
    }
}
