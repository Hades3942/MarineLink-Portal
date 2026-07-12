package ac.tz.suza.marinelink_portal.ExceptionHandler;

public class AnalyticsDataNotFoundException extends RuntimeException {
    public AnalyticsDataNotFoundException(String metric) {
        super("No analytics data found for metric: " + metric);
    }
}
