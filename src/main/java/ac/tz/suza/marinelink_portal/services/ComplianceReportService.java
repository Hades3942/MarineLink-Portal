package ac.tz.suza.marinelink_portal.services;

import ac.tz.suza.marinelink_portal.models.ComplianceReport;
import ac.tz.suza.marinelink_portal.models.Transactions;
import ac.tz.suza.marinelink_portal.repositories.ComplianceReportRepository;
import ac.tz.suza.marinelink_portal.repositories.TransactionsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComplianceReportService {

    private final TransactionsRepository transactionsRepository;
    private final ComplianceReportRepository reportRepository;

    public ComplianceReportService(TransactionsRepository transactionsRepository,
                                   ComplianceReportRepository reportRepository) {
        this.transactionsRepository = transactionsRepository;
        this.reportRepository = reportRepository;
    }

    // ============================
    // DAILY REPORT
    // ============================
    public ComplianceReport generateDailyReport(String admin) {
        LocalDate today = LocalDate.now();

        List<Transactions> dailyTransactions = transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getTimestamp().toLocalDate().isEqual(today))
                .collect(Collectors.toList());

        Map<String, Object> summary = buildSummary(dailyTransactions);

        return saveReport("DAILY", summary, admin);
    }

    // ============================
    // WEEKLY REPORT
    // ============================
    public ComplianceReport generateWeeklyReport(String admin) {
        LocalDate weekAgo = LocalDate.now().minusDays(7);

        List<Transactions> weeklyTransactions = transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getTimestamp().toLocalDate().isAfter(weekAgo))
                .collect(Collectors.toList());

        Map<String, Object> summary = buildSummary(weeklyTransactions);

        return saveReport("WEEKLY", summary, admin);
    }

    // ============================
    // MONTHLY REPORT
    // ============================
    public ComplianceReport generateMonthlyReport(String admin) {
        LocalDate monthAgo = LocalDate.now().minusDays(30);

        List<Transactions> monthlyTransactions = transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getTimestamp().toLocalDate().isAfter(monthAgo))
                .collect(Collectors.toList());

        Map<String, Object> summary = buildSummary(monthlyTransactions);

        return saveReport("MONTHLY", summary, admin);
    }

    // ============================
    // SUMMARY BUILDER
    // ============================
    private Map<String, Object> buildSummary(List<Transactions> transactions) {

        double totalRevenue = transactions.stream()
                .mapToDouble(Transactions::getTotalPrice)
                .sum();

        long totalTransactions = transactions.size();

        Map<Long, Double> fisherPerformance = new HashMap<>();
        Map<Long, Long> buyerActivity = new HashMap<>();

        for (Transactions t : transactions) {
            fisherPerformance.merge(t.getFisherId(), t.getTotalPrice(), Double::sum);
            buyerActivity.merge(t.getBuyerId(), 1L, Long::sum);
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRevenue", totalRevenue);
        summary.put("totalTransactions", totalTransactions);
        summary.put("fisherPerformance", fisherPerformance);
        summary.put("buyerActivity", buyerActivity);

        return summary;
    }

    // ============================
    // SAVE REPORT
    // ============================
    private ComplianceReport saveReport(String period, Map<String, Object> summary, String admin) {
        ComplianceReport report = new ComplianceReport();
        report.setPeriod(period);
        report.setSummaryJson(summary.toString());
        report.setGeneratedAt(LocalDateTime.now());
        report.setGeneratedBy(admin);

        return reportRepository.save(report);
    }
}
