package ac.tz.suza.marinelink_portal.ExceptionHandler;

//============================
// CUSTOM EXCEPTIONS
//============================
public class ListingNotFoundException extends RuntimeException {
    public ListingNotFoundException(Long id) {
        super("Listing with ID " + id + " not found");
    }

    // ============================
    // HANDLE INVALID LISTING DATA
    // ============================
    public class InvalidListingDataException extends RuntimeException {
    public InvalidListingDataException(String message) {
        super(message);
    }
}
}
