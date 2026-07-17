package ac.tz.suza.marinelink_portal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ac.tz.suza.marinelink_portal.Dto.LoginRequest;
import ac.tz.suza.marinelink_portal.Dto.RegisterRequest;
import ac.tz.suza.marinelink_portal.services.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest req) {
    return ResponseEntity.ok(authService.login(req));
}

}
