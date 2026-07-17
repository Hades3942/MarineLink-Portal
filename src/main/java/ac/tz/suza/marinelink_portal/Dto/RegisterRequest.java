package ac.tz.suza.marinelink_portal.Dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String zanzibarId;
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String role;
}

