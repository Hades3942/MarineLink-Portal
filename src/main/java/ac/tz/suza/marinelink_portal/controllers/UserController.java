package ac.tz.suza.marinelink_portal.controllers;

import ac.tz.suza.marinelink_portal.models.User;
import ac.tz.suza.marinelink_portal.Dto.UserUpdateDto;
import ac.tz.suza.marinelink_portal.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET ALL USERS
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // SEARCH USERS
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String q) {
        return userService.searchUsers(q);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDto dto) {

        try {
            User updated = userService.updateUser(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update failed");
        }
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete failed");
        }
    }

    // ROLE COUNT (Admin Reports)
    @GetMapping("/roles/count")
    public Map<String, Long> countRoles() {
        return userService.countRoles();
    }
}
