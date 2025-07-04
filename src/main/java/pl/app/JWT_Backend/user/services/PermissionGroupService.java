package pl.app.JWT_Backend.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.JWT_Backend.user.dto.PermissionGroupDto;
import pl.app.JWT_Backend.user.models.PermissionGroup;
import pl.app.JWT_Backend.user.repositories.PermissionGroupRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionGroupService {
    private final PermissionGroupRepository permissionGroupRepository;

    public List<PermissionGroup> getAllPermissionGroups(){
        return this.permissionGroupRepository.findAll();
    }

    public PermissionGroup createPermissionGroup(PermissionGroupDto permissionGroupDto) {
        PermissionGroup permissionGroup = new PermissionGroup();
        LocalDateTime now = LocalDateTime.now();

        permissionGroup.setName(permissionGroupDto.getName());
        permissionGroup.setCreatedAt(now);
        permissionGroup.setUpdatedAt(now);
        return permissionGroupRepository.save(permissionGroup);
    }
}
