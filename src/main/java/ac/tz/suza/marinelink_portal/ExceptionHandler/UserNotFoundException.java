package ac.tz.suza.marinelink_portal.ExceptionHandler;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with ID " + id + " not found");
    }



    // ============================
    // HANDLE EMAIL ALREADY USED
    // ============================
    public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("Email already used: " + email);
    }
}
}
