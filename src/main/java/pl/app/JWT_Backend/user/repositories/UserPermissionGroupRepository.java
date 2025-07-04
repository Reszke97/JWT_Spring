package pl.app.JWT_Backend.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.JWT_Backend.user.models.AppUser;
import pl.app.JWT_Backend.user.models.UserPermissionGroup;

import java.util.List;

@Repository
public interface UserPermissionGroupRepository extends JpaRepository<UserPermissionGroup, Long> {
    List<UserPermissionGroup> findByAppUserId(Long userId);
    List<UserPermissionGroup> findByAppUser(AppUser appUser);
}
