package ac.tz.suza.marinelink_portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.tz.suza.marinelink_portal.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
