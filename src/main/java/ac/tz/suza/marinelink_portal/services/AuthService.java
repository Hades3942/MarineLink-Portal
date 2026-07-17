package ac.tz.suza.marinelink_portal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ac.tz.suza.marinelink_portal.Dto.LoginRequest;
import ac.tz.suza.marinelink_portal.Dto.RegisterRequest;
import ac.tz.suza.marinelink_portal.models.Role;
import ac.tz.suza.marinelink_portal.models.User;
import ac.tz.suza.marinelink_portal.repositories.UserRepository;
import ac.tz.suza.marinelink_portal.security.JwtUtil;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ============================
    // REGISTER USER
    // ============================
    public ResponseEntity<?> register(RegisterRequest req) {

        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setZanzibarId(req.getZanzibarId());
        user.setFullName(req.getFullName());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        // Default role if not provided
        Role role = (req.getRole() == null)
                ? Role.FISHER
                : Role.valueOf(req.getRole());

        user.setRole(role);

        userRepository.save(user);

        return ResponseEntity.ok(Map.of("success", true));
    }

    // ============================
    // LOGIN USER
    // ============================
    public Map<String, Object> login(LoginRequest req) {

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user);

        return Map.of(
                "token", token,
                "role", user.getRole().name(),
                "userId", user.getId(),
                "username", user.getUsername()
        );
    }
}
