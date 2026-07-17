package ac.tz.suza.marinelink_portal.Dto;

import ac.tz.suza.marinelink_portal.models.*;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String zanzibarId;
    private String fullName;
    private String email;
    private String username;
    private Role role;
}
