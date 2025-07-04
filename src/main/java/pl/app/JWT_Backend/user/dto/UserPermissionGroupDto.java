package pl.app.JWT_Backend.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.app.JWT_Backend.user.models.PermissionGroup;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class UserPermissionGroupDto {
    private Long id;
    private PermissionGroup permissionGroup;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}