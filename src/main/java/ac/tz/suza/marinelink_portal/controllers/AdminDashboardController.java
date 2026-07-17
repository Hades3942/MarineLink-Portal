package ac.tz.suza.marinelink_portal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ac.tz.suza.marinelink_portal.models.User;
import ac.tz.suza.marinelink_portal.services.AdminDashboardService;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@CrossOrigin
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    // ============================
    // DASHBOARD SUMMARY
    // ============================
    @GetMapping("/summary")
    public ResponseEntity<?> getDashboardSummary() {
        return ResponseEntity.ok(adminDashboardService.getDashboardSummary());
    }

    // ============================
    // ALL USERS (ADMIN PAGE)
    // ============================
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(adminDashboardService.getAllUsers());
    }

    // ============================
    // COMPLIANCE REPORT HISTORY
    // ============================
    @GetMapping("/reports")
    public ResponseEntity<?> getReportHistory() {
        return ResponseEntity.ok(adminDashboardService.getReportHistory());
    }

    // ============================
    // UPDATE USER ROLE
    // ============================
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser) {
        User Updated = adminDashboardService.updateUser(id, updatedUser);
        return ResponseEntity.ok(Updated);
    }

    // ============================
    // DELETE USER
    // ============================
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        adminDashboardService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
