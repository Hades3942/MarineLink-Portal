package ac.tz.suza.marinelink_portal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ac.tz.suza.marinelink_portal.models.Role;
import ac.tz.suza.marinelink_portal.models.User;
import ac.tz.suza.marinelink_portal.repositories.UserRepository;
import ac.tz.suza.marinelink_portal.security.JwtUtil;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseEntity<?> register(User req) {

        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        req.setPassword(passwordEncoder.encode(req.getPassword()));

        // Default role if not provided
        if (req.getRole() == null) {
            req.setRole(Role.FISHER);
        }

        userRepository.save(req);

        return ResponseEntity.ok("Registration successful");
    }

    public ResponseEntity<?> login(String username, String password) {

        var userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid username");
        }

        var user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(token);
    }
}
