package pl.app.JWT_Backend.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.app.JWT_Backend.user.models.PermissionAssignedToGroup;
import pl.app.JWT_Backend.user.models.PermissionGroup;

import java.util.List;

public interface PermissionAssignedToGroupRepository extends JpaRepository<PermissionAssignedToGroup, Long> {
    List<PermissionAssignedToGroup> findByPermissionGroupId(Long permissionGroupId);

    List<PermissionAssignedToGroup> findByPermissionGroupIdIn(List<Long> permissionGroupIds);

    List<PermissionAssignedToGroup> findByPermissionGroup(PermissionGroup permissionGroup);
}