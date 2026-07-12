package ac.tz.suza.marinelink_portal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ac.tz.suza.marinelink_portal.Dto.LoginRequest;
import ac.tz.suza.marinelink_portal.models.User;
import ac.tz.suza.marinelink_portal.services.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User req) {
        return authService.register(req);
    }

    @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest req) {
    return authService.login(req.getUsername(), req.getPassword());
}

}