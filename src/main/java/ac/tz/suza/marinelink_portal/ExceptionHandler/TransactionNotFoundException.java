package ac.tz.suza.marinelink_portal.ExceptionHandler;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id) {
        super("Transaction with ID " + id + " not found");
    }

    // ============================
    // HANDLE INSUFFICIENT QUANTITY
    // ============================
public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException(double requested, double available) {
        super("Requested " + requested + "kg but only " + available + "kg available");
    }
}

    // ============================
    // HANDLE LISTING UNAVAILABLE
    // ============================

public class ListingUnavailableException extends RuntimeException {
    public ListingUnavailableException(Long id) {
        super("Listing " + id + " is not available for purchase");
    }
}


}
