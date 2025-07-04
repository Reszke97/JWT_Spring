package pl.app.JWT_Backend.user.dto;

import lombok.Data;

@Data
public class PermissionsAssignedToGroupDto {
    private Long id;
    private PermissionGroupDto permissionGroup;
    private PermissionDto permission;
}
