package pl.app.JWT_Backend.user.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.app.JWT_Backend.user.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
