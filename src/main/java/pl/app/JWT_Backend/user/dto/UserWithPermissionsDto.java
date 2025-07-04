package pl.app.JWT_Backend.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.app.JWT_Backend.user.models.Department;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UserWithPermissionsDto {
    private Long id;
    private String name;
    private String surname;
    private Department department;
    private List<UserPermissionGroupDto> userPermissionGroups;
}