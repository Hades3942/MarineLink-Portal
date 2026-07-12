package ac.tz.suza.marinelink_portal.ExceptionHandler;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(Long id) {
        super("Report with ID " + id + " not found");
    }

    // ============================
    // HANDLE INVALID REPORT PERIOD
    // ============================

public class InvalidReportPeriodException extends RuntimeException {
    public InvalidReportPeriodException(String period) {
        super("Invalid report period: " + period);
    }
}

}
