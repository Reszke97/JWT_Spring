package pl.app.JWT_Backend.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.app.JWT_Backend.user.models.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    Boolean existsByUsername(String username);
}
