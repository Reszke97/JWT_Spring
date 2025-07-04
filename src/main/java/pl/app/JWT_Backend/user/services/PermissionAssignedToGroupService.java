package pl.app.JWT_Backend.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.JWT_Backend.user.dto.PermissionDto;
import pl.app.JWT_Backend.user.dto.PermissionGroupDto;
import pl.app.JWT_Backend.user.dto.PermissionsAssignedToGroupDto;
import pl.app.JWT_Backend.user.dto.PermissionsAssignedToGroupWithoutObjectsDto;
import pl.app.JWT_Backend.user.models.Permission;
import pl.app.JWT_Backend.user.models.PermissionAssignedToGroup;
import pl.app.JWT_Backend.user.models.PermissionGroup;
import pl.app.JWT_Backend.user.repositories.PermissionAssignedToGroupRepository;
import pl.app.JWT_Backend.user.repositories.PermissionGroupRepository;
import pl.app.JWT_Backend.user.repositories.PermissionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionAssignedToGroupService {
    private final PermissionAssignedToGroupRepository permissionAssignedToGroupRepository;
    private final PermissionGroupRepository permissionGroupRepository;
    private final PermissionRepository permissionRepository;

    private static PermissionsAssignedToGroupDto getPermissionsAssignedToGroupDto(PermissionGroup permissionGroup, Permission permission, PermissionAssignedToGroup newPermissionAssignedToGroup) {
        PermissionsAssignedToGroupDto permissionsAssignedToGroupDto = new PermissionsAssignedToGroupDto();
        PermissionGroupDto permissionGroupDto = new PermissionGroupDto();
        PermissionDto permissionDto = new PermissionDto();

        permissionGroupDto.setName(permissionGroup.getName());
        permissionGroupDto.setId(permissionGroup.getId());

        permissionDto.setId(permission.getId());
        permissionDto.setName(permission.getName());
        permissionDto.setDescriptionInPolish(permission.getDescriptionInPolish());
        permissionDto.setDescriptionInEnglish(permission.getDescriptionInEnglish());


        permissionsAssignedToGroupDto.setPermission(permissionDto);
        permissionsAssignedToGroupDto.setPermissionGroup(permissionGroupDto);
        permissionsAssignedToGroupDto.setId(newPermissionAssignedToGroup.getId());
        return permissionsAssignedToGroupDto;
    }

    public List<PermissionsAssignedToGroupDto> getPermissionsAssignedToGroup(Long permissionGroupId){
        List<PermissionAssignedToGroup> entities = permissionAssignedToGroupRepository.findByPermissionGroupId(permissionGroupId);

        return entities.stream()
                .map(entity -> {
                    PermissionsAssignedToGroupDto permissionsAssignedToGroupDto = new PermissionsAssignedToGroupDto();
                    permissionsAssignedToGroupDto.setId(entity.getId());

                    PermissionDto permissionDto = new PermissionDto();
                    permissionDto.setId(entity.getPermission().getId());
                    permissionDto.setName(entity.getPermission().getName());
                    permissionDto.setDescriptionInPolish(entity.getPermission().getDescriptionInPolish());
                    permissionDto.setDescriptionInEnglish(entity.getPermission().getDescriptionInEnglish());

                    PermissionGroupDto permissionGroupDto = new PermissionGroupDto();
                    permissionGroupDto.setId(entity.getPermissionGroup().getId());
                    permissionGroupDto.setName(entity.getPermissionGroup().getName());

                    permissionsAssignedToGroupDto.setPermission(permissionDto);
                    permissionsAssignedToGroupDto.setPermissionGroup(permissionGroupDto);

                    return  permissionsAssignedToGroupDto;
                })
                .collect(Collectors.toList());
    }


    public List<PermissionsAssignedToGroupDto> savePermissionsAssignedToGroup(PermissionsAssignedToGroupWithoutObjectsDto permissionGroups){
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionGroups.getPermissionGroupId()).orElseThrow(() -> new IllegalArgumentException("Permission Group not found for ID: " + permissionGroups.getPermissionGroupId()));
        return permissionGroups.getPermissionsIdToAdd().stream()
                .map(permissionId -> {
                    LocalDateTime now = LocalDateTime.now();
                    PermissionAssignedToGroup newPermissionAssignedToGroup = new PermissionAssignedToGroup();
                    Permission permission = permissionRepository.findById(permissionId).orElseThrow(() -> new IllegalArgumentException("Permission not found for ID: " + permissionId));

                    newPermissionAssignedToGroup.setPermission(permission);
                    newPermissionAssignedToGroup.setPermissionGroup(permissionGroup);
                    newPermissionAssignedToGroup.setCreatedAt(now);
                    newPermissionAssignedToGroup.setUpdatedAt(now);
                    permissionAssignedToGroupRepository.save(newPermissionAssignedToGroup);

                    return getPermissionsAssignedToGroupDto(permissionGroup, permission, newPermissionAssignedToGroup);

                })
                .collect(Collectors.toList());
    }

    public void deletePermissionAssignedToGroup(Long id){
        PermissionAssignedToGroup permissionAssignedToGroup = permissionAssignedToGroupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Permission assigned to group with the id: " + id + "not found."));
        permissionAssignedToGroupRepository.delete(permissionAssignedToGroup);
    }
}
