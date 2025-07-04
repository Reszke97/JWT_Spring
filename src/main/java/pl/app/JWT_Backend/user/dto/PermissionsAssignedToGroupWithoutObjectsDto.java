package pl.app.JWT_Backend.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class PermissionsAssignedToGroupWithoutObjectsDto {
    private Long permissionGroupId;
    private List<Long> permissionsIdToAdd;
}
