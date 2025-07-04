package pl.app.JWT_Backend.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.app.JWT_Backend.user.models.PermissionGroup;

public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {
}
