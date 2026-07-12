package ac.tz.suza.marinelink_portal.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ac.tz.suza.marinelink_portal.ExceptionHandler.ReportNotFoundException.InvalidReportPeriodException;
import ac.tz.suza.marinelink_portal.ExceptionHandler.TransactionNotFoundException.InsufficientQuantityException;
import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalException {


    @ExceptionHandler(ListingNotFoundException.class)
public ResponseEntity<?> handleListingNotFound(ListingNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(ex, 404));
}

@ExceptionHandler(InsufficientQuantityException.class)
public ResponseEntity<?> handleInsufficientQuantity(InsufficientQuantityException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error(ex, 400));
}

@ExceptionHandler(UserNotFoundException.class)
public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(ex, 404));
}

@ExceptionHandler(InvalidReportPeriodException.class)
public ResponseEntity<?> handleInvalidPeriod(InvalidReportPeriodException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error(ex, 400));
}

    private Map<String, Object> error(Exception ex, int status) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "error", ex.getMessage(),
                "status", status
        );
    }
}
