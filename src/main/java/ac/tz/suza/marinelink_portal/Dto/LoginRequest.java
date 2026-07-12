package ac.tz.suza.marinelink_portal.Dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}