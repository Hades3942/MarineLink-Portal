package ac.tz.suza.marinelink_portal.services;

import ac.tz.suza.marinelink_portal.models.User;
import ac.tz.suza.marinelink_portal.models.Role;
import ac.tz.suza.marinelink_portal.repositories.UserRepository;
import ac.tz.suza.marinelink_portal.Dto.UserUpdateDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // SEARCH USERS
    public List<User> searchUsers(String search) {
        if (search == null || search.isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.searchUsers(search);
    }

    // UPDATE USER
    public User updateUser(Long id, UserUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setZanzibarId(dto.getZanzibarId());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());

        return userRepository.save(user);
    }

    // DELETE USER
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // COUNT ROLES (Admin Reports)
    public Map<String, Long> countRoles() {
        Map<String, Long> roles = new HashMap<>();
        roles.put("admin", userRepository.countByRole(Role.ADMIN));
        roles.put("fisher", userRepository.countByRole(Role.FISHER));
        roles.put("buyer", userRepository.countByRole(Role.BUYER));
        roles.put("regulator", userRepository.countByRole(Role.REGULATOR));
        return roles;
    }
}
